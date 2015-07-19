package home.websocket;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import home.model.Device;

@ApplicationScoped
public class DeviceSessionHandler {
	 private int deviceId = 0;
	    private final Set<Session> sessions = new HashSet<Session>();
	    private final Set<Device> devices = new HashSet<Device>();
	    
    public void addSession(Session session) {
    	System.out.println("In addSession flow..."+session.getId());
    	System.out.println("In addSession flow..."+session.toString());
    	sessions.add(session);
    	System.out.println("Number of Device: "+devices.size());
    	
        for (Device device : devices) {
        	System.out.println("Adding devices into Session");
            JsonObject addMessage = createAddMessage(device);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public List getDevices() {
        return new ArrayList<>(devices);
    }

    public void addDevice(Device device) {
        device.setId(deviceId);
        devices.add(device);
        deviceId++;
        JsonObject addMessage = createAddMessage(device);
        sendToAllConnectedSessions(addMessage);
    }

    public void removeDevice(int id) {
    	Device device = getDeviceById(id);
        if (device != null) {
            devices.remove(device);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    public void toggleDevice(int id) {
    	 JsonProvider provider = JsonProvider.provider();
         Device device = getDeviceById(id);
         if (device != null) {
             if ("On".equals(device.getStatus())) {
                 device.setStatus("Off");
             } else {
                 device.setStatus("On");
             }
             JsonObject updateDevMessage = provider.createObjectBuilder()
                     .add("action", "toggle")
                     .add("id", device.getId())
                     .add("status", device.getStatus())
                     .build();
             sendToAllConnectedSessions(updateDevMessage);
         }
         
    }


    private Device getDeviceById(int id) {
        for (Device device : devices) {
            if (device.getId() == id) {
                return device;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(Device device) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", device.getId())
                .add("name", device.getName())
                .add("type", device.getType())
                .add("status", device.getStatus())
                .add("description", device.getDescription())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
    	System.out.println("Updating all sessions with size: "+ sessions.size());
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
        	Logger.getLogger(DeviceSessionHandler.class.getName()).log(Level.INFO, message.toString());
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(DeviceSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

import java.nio.file.*;

import java.io.*;
public class WatchServiceExample {

	public static void main(String[] args) throws IOException {
		
		WatchService watcher=FileSystems.getDefault().newWatchService();
		Path dir = Paths.get("C:\\Users\\Administrator\\Desktop\\Core_Java");
		WatchKey key= dir.register(watcher,StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
		
		while (true) {
		   
		    try {
		        // wait for a key to be available
		        key = watcher.take();
		    } catch (InterruptedException ex) {
		        return;
		    }
		    for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                 
                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();
                 
                System.out.println(kind.name() + ": " + fileName);
                 
                if (kind == StandardWatchEventKinds.ENTRY_MODIFY &&
                        fileName.toString().equals("DirectoryWatchDemo.java")) {
                    System.out.println("My source file has changed!!!");
                }
            }
             
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
         
    }
}

package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {

    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(out))) {
            dos.writeInt(tasks.size());
            for (Task t : tasks) {
                dos.writeInt(t.getTitle().length());
                dos.writeUTF(t.getTitle());
                if (t.isActive()) {
                    dos.writeInt(1);
                } else {
                    dos.writeInt(0);
                }
                dos.writeInt(t.getRepeatInterval());
                if (t.isRepeated()) {
                    dos.writeLong(t.getStartTime().toEpochSecond(ZoneOffset.UTC));
                    dos.writeLong(t.getEndTime().toEpochSecond(ZoneOffset.UTC));
                } else {
                    dos.writeLong(t.getTime().toEpochSecond(ZoneOffset.UTC));
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: handle exception
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(in))) {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                int titleLength = dis.readInt();
                String title = dis.readUTF();
                boolean active = (dis.readInt() == 1);
                int interval = dis.readInt();

                Task t;
                if (interval > 0) {
                    t = new Task(title,
                            LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC),
                            LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC),
                            interval);
                } else {
                    t = new Task(title,
                            LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC));
                }
                t.setActive(active);
                tasks.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: handle exception
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            write(tasks, bos);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: handle exception
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            read(tasks, bis);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: handle exception
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        String json = new Gson().toJson(tasks);
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        try (BufferedReader reader = new BufferedReader(in)) {
            String json;
            while ((json = reader.readLine()) != null) {
                AbstractTaskList atl = new Gson().fromJson(json, tasks.getClass());
                for (Task t : atl) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        String json = new Gson().toJson(tasks);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String json;
            while ((json = reader.readLine()) != null) {
                AbstractTaskList atl = new Gson().fromJson(json, tasks.getClass());
                for (Task t : atl) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

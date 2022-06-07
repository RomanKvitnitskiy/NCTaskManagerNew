package ua.edu.sumdu.j2se.kvitnytskyi.tasks.model;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

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

/**
 * Class for input/output of data about tasks.
 *
 * @author kvitnytskyi
 */
public class TaskIO {
    private static final Logger log = Logger.getLogger(TaskIO.class);

    /**
     * Writes tasks from the list to the stream in binary format.
     *
     * @param tasks - task list
     * @param out   - output stream
     * @throws IOException if the writing was failed
     */
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
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
            log.error(e);
            throw e;
        }
    }

    /**
     * Reads tasks from the stream to the given task list.
     *
     * @param tasks - task list
     * @param in    - input stream
     * @throws IOException if the reading was failed
     */
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
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
            log.error(e);
            throw e;
        }
    }

    /**
     * Writes tasks from the list to a file.
     *
     * @param tasks - task list
     * @param file  - output file
     * @throws IOException if the writing was failed
     */
    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            write(tasks, bos);
        } catch (IOException e) {
            log.error(e);
            throw e;
        }
    }

    /**
     * Reads tasks from a file to the specified task list.
     *
     * @param tasks - task list
     * @param file  - input file
     * @throws IOException if the reading was failed
     */
    public static void readBinary(AbstractTaskList tasks, File file) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            read(tasks, bis);
        } catch (IOException e) {
            log.error(e);
            throw e;
        }
    }

    /**
     * Writes tasks from the list to the stream in JSON format.
     *
     * @param tasks - task list
     * @param out   - output stream
     * @throws IOException if the writing was failed
     */
    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        String json = new Gson().toJson(tasks);
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            log.error(e);
            throw e;
        }
    }

    /**
     * Reads tasks from the stream to the list.
     *
     * @param tasks - task list
     * @param in    - input stream
     * @throws IOException if the reading was failed
     */
    public static void read(AbstractTaskList tasks, Reader in) throws IOException {
        try (BufferedReader reader = new BufferedReader(in)) {
            String json;
            while ((json = reader.readLine()) != null) {
                AbstractTaskList atl = new Gson().fromJson(json, tasks.getClass());
                for (Task t : atl) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            log.error(e);
            throw e;
        }
    }

    /**
     * Writes tasks to a file in JSON format.
     *
     * @param tasks - task list
     * @param file  - JSON output file
     * @throws IOException if the writing was failed
     */
    public static void writeText(AbstractTaskList tasks, File file) throws IOException {
        String json = new Gson().toJson(tasks);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            log.error(e);
            throw e;
        }
    }

    /**
     * Reads tasks from a file.
     *
     * @param tasks - task list
     * @param file  - JSON input file
     * @throws IOException if the reading was failed
     */
    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String json;
            while ((json = reader.readLine()) != null) {
                AbstractTaskList atl = new Gson().fromJson(json, tasks.getClass());
                for (Task t : atl) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            log.error(e);
            throw e;
        }
    }
}

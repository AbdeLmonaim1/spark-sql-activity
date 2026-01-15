package ma.enset.data;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataGenerator {
    private static final String[] STATIONS = {
            "Station A", "Station B", "Station C", "Station D", "Station E"
    };

    private static final String[] GENDERS = {"M", "F"};
    private static final Random random = new Random();

    public static void main(String[] args) {
        generateCSV("bike_sharing.csv", 1000);
        System.out.println("Sample data generated: bike_sharing.csv");
    }

    public static void generateCSV(String filename, int numRecords) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (FileWriter writer = new FileWriter(filename)) {
            // Write header
            writer.write("rental_id,user_id,age,gender,start_time,end_time," +
                    "start_station,end_station,duration_minutes,price\n");

            for (int i = 1; i <= numRecords; i++) {
                int userId = random.nextInt(500) + 1;
                int age = random.nextInt(63) + 18; // 18-80
                String gender = GENDERS[random.nextInt(2)];

                // Random date in 2024
                LocalDateTime startTime = LocalDateTime.of(2024,
                        random.nextInt(12) + 1,
                        random.nextInt(28) + 1,
                        random.nextInt(24),
                        random.nextInt(60));

                int duration = random.nextInt(120) + 5; // 5-125 minutes
                LocalDateTime endTime = startTime.plusMinutes(duration);

                String startStation = STATIONS[random.nextInt(STATIONS.length)];
                String endStation = STATIONS[random.nextInt(STATIONS.length)];

                double price = Math.round((2.5 + duration * 0.1) * 100.0) / 100.0;

                writer.write(String.format("%d,%d,%d,%s,%s,%s,%s,%s,%d,%.2f\n",
                        i, userId, age, gender,
                        startTime.format(formatter),
                        endTime.format(formatter),
                        startStation, endStation, duration, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

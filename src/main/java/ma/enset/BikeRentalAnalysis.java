package ma.enset;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

import static org.apache.spark.sql.functions.*;

public class BikeRentalAnalysis {
        public static void main(String[] args) {
                // Initialize Spark Session
                SparkSession spark = SparkSession.builder()
                                .appName("Bike Sharing Analysis")
                                .master("local[*]")
                                .config("spark.sql.shuffle.partitions", "4")
                                .getOrCreate();

                spark.sparkContext().setLogLevel("WARN");

                System.out.println("\n========================================");
                System.out.println("BIKE SHARING ANALYSIS - SPARK SQL");
                System.out.println("========================================\n");

                try {
                        // ===================================
                        // EXERCISE 1: DATA LOADING & EXPLORATION
                        // ===================================
                        System.out.println("\n--- EXERCISE 1: Data Loading & Exploration ---\n");

                        // 1. Load CSV file - VÉRIFIER QUE LE FICHIER EXISTE
                        System.out.println("1. Loading data from bike_sharing.csv...");

                        // Obtenir le chemin absolu du fichier
                        String csvPath = new java.io.File("bike_sharing.csv").getAbsolutePath();
                        System.out.println("Chemin du fichier: " + csvPath);

                        // Vérifier que le fichier existe
                        java.io.File csvFile = new java.io.File(csvPath);
                        if (!csvFile.exists()) {
                                System.err.println("ERREUR: Le fichier n'existe pas à: " + csvPath);
                                System.err.println("Répertoire de travail actuel: " + System.getProperty("user.dir"));
                                System.err.println(
                                                "\nVeuillez d'abord exécuter DataGenerator.java pour générer le fichier.");
                                throw new RuntimeException("Fichier bike_sharing.csv introuvable");
                        }

                        Dataset<Row> df = spark.read()
                                        .option("header", "true")
                                        .option("inferSchema", "true")
                                        .csv(csvPath);

                        // Vérifier que les données sont chargées
                        if (df.isEmpty()) {
                                System.err.println("ERREUR: Le fichier bike_sharing.csv est vide!");
                                System.err.println(
                                                "Veuillez d'abord exécuter DataGenerator.java pour générer le fichier.");
                                throw new RuntimeException("Fichier bike_sharing.csv est vide");
                        }

                        System.out.println("✓ Data loaded successfully");

                        // 2. Display schema
                        System.out.println("\n2. Schema:");
                        df.printSchema();

                        // 3. Show first 5 rows
                        System.out.println("\n3. First 5 rows:");
                        df.show(5, false);

                        // 4. Count total rentals
                        long totalRentals = df.count();
                        System.out.println("\n4. Total number of rentals: " + totalRentals + "\n");

                        // ===================================
                        // EXERCISE 2: CREATE TEMPORARY VIEW
                        // ===================================
                        System.out.println("\n--- EXERCISE 2: Create Temporary View ---\n");

                        // CRÉATION DE LA VUE TEMPORAIRE - CETTE ÉTAPE EST CRUCIALE
                        df.createOrReplaceTempView("bike_rentals_view");
                        System.out.println("✓ Temporary view 'bike_rentals_view' created");

                        // Vérifier que la vue est accessible
                        spark.sql("SELECT COUNT(*) as count FROM bike_rentals_view").show();

                        // ===================================
                        // EXERCISE 3: BASIC SQL QUERIES
                        // ===================================
                        System.out.println("\n--- EXERCISE 3: Basic SQL Queries ---\n");

                        // 1. Rentals longer than 30 minutes
                        System.out.println("1. Rentals longer than 30 minutes:");
                        Dataset<Row> longRentals = spark.sql(
                                        "SELECT rental_id, user_id, start_station, end_station, duration_minutes " +
                                                        "FROM bike_rentals_view " +
                                                        "WHERE duration_minutes > 30 " +
                                                        "ORDER BY duration_minutes DESC");
                        longRentals.show(10, false);
                        System.out.println("Total: " + longRentals.count() + " rentals\n");

                        // 2. Rentals starting at Station A
                        System.out.println("\n2. Rentals starting at 'Station A':");
                        Dataset<Row> stationARentals = spark.sql(
                                        "SELECT rental_id, user_id, start_time, end_station, duration_minutes " +
                                                        "FROM bike_rentals_view " +
                                                        "WHERE start_station = 'Station A'");
                        stationARentals.show(10, false);
                        System.out.println("Total: " + stationARentals.count() + " rentals\n");

                        // 3. Total revenue
                        System.out.println("\n3. Total Revenue:");
                        Dataset<Row> revenue = spark.sql(
                                        "SELECT ROUND(SUM(price), 2) as total_revenue " +
                                                        "FROM bike_rentals_view");
                        revenue.show(false);

                        // ===================================
                        // EXERCISE 4: AGGREGATION QUERIES
                        // ===================================
                        System.out.println("\n--- EXERCISE 4: Aggregation Queries ---\n");

                        // 1. Count rentals per start station
                        System.out.println("1. Rentals count by start station:");
                        Dataset<Row> rentalsByStation = spark.sql(
                                        "SELECT start_station, COUNT(*) as rental_count " +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY start_station " +
                                                        "ORDER BY rental_count DESC");
                        rentalsByStation.show(false);

                        // 2. Average duration per start station
                        System.out.println("\n2. Average rental duration by start station:");
                        Dataset<Row> avgDuration = spark.sql(
                                        "SELECT start_station, " +
                                                        "       ROUND(AVG(duration_minutes), 2) as avg_duration_minutes "
                                                        +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY start_station " +
                                                        "ORDER BY avg_duration_minutes DESC");
                        avgDuration.show(false);

                        // 3. Station with highest number of rentals
                        System.out.println("\n3. Station with highest number of rentals:");
                        Dataset<Row> topStation = spark.sql(
                                        "SELECT start_station, COUNT(*) as rental_count " +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY start_station " +
                                                        "ORDER BY rental_count DESC " +
                                                        "LIMIT 1");
                        topStation.show(false);

                        // ===================================
                        // EXERCISE 5: TIME-BASED ANALYSIS
                        // ===================================
                        System.out.println("\n--- EXERCISE 5: Time-Based Analysis ---\n");

                        // 1. Extract hour from start_time
                        System.out.println("1. Sample of extracted hours:");
                        Dataset<Row> withHour = spark.sql(
                                        "SELECT rental_id, start_time, HOUR(start_time) as hour " +
                                                        "FROM bike_rentals_view " +
                                                        "LIMIT 10");
                        withHour.show(false);

                        // 2. Rentals per hour (peak hours)
                        System.out.println("\n2. Bikes rented per hour (Peak Hours Analysis):");
                        Dataset<Row> rentalsByHour = spark.sql(
                                        "SELECT HOUR(start_time) as hour, COUNT(*) as rental_count " +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY HOUR(start_time) " +
                                                        "ORDER BY hour");
                        rentalsByHour.show(24, false);

                        System.out.println("\nTop 3 Peak Hours:");
                        Dataset<Row> peakHours = spark.sql(
                                        "SELECT HOUR(start_time) as hour, COUNT(*) as rental_count " +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY HOUR(start_time) " +
                                                        "ORDER BY rental_count DESC " +
                                                        "LIMIT 3");
                        peakHours.show(false);

                        // 3. Most popular start station during morning (7-12)
                        System.out.println("\n3. Most popular start station during morning (7-12):");
                        Dataset<Row> morningStation = spark.sql(
                                        "SELECT start_station, COUNT(*) as rental_count " +
                                                        "FROM bike_rentals_view " +
                                                        "WHERE HOUR(start_time) BETWEEN 7 AND 12 " +
                                                        "GROUP BY start_station " +
                                                        "ORDER BY rental_count DESC " +
                                                        "LIMIT 1");
                        morningStation.show(false);

                        // ===================================
                        // EXERCISE 6: USER BEHAVIOR ANALYSIS
                        // ===================================
                        System.out.println("\n--- EXERCISE 6: User Behavior Analysis ---\n");

                        // 1. Average age of users
                        System.out.println("1. Average age of users:");
                        Dataset<Row> avgAge = spark.sql(
                                        "SELECT ROUND(AVG(age), 2) as average_age " +
                                                        "FROM bike_rentals_view");
                        avgAge.show(false);

                        // 2. Count users by gender
                        System.out.println("\n2. Rentals count by gender:");
                        Dataset<Row> byGender = spark.sql(
                                        "SELECT gender, COUNT(*) as rental_count " +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY gender " +
                                                        "ORDER BY rental_count DESC");
                        byGender.show(false);

                        // 3. Rentals by age group
                        System.out.println("\n3. Rentals by age group:");
                        Dataset<Row> byAgeGroup = spark.sql(
                                        "SELECT " +
                                                        "  CASE " +
                                                        "    WHEN age BETWEEN 18 AND 30 THEN '18-30' " +
                                                        "    WHEN age BETWEEN 31 AND 40 THEN '31-40' " +
                                                        "    WHEN age BETWEEN 41 AND 50 THEN '41-50' " +
                                                        "    WHEN age >= 51 THEN '51+' " +
                                                        "    ELSE 'Unknown' " +
                                                        "  END as age_group, " +
                                                        "  COUNT(*) as rental_count " +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY " +
                                                        "  CASE " +
                                                        "    WHEN age BETWEEN 18 AND 30 THEN '18-30' " +
                                                        "    WHEN age BETWEEN 31 AND 40 THEN '31-40' " +
                                                        "    WHEN age BETWEEN 41 AND 50 THEN '41-50' " +
                                                        "    WHEN age >= 51 THEN '51+' " +
                                                        "    ELSE 'Unknown' " +
                                                        "  END " +
                                                        "ORDER BY rental_count DESC");
                        byAgeGroup.show(false);

                        // ===================================
                        // BONUS: ADDITIONAL INSIGHTS
                        // ===================================
                        System.out.println("\n--- BONUS: Additional Insights ---\n");

                        // Most profitable route
                        System.out.println("Most profitable route:");
                        Dataset<Row> profitableRoute = spark.sql(
                                        "SELECT start_station, end_station, " +
                                                        "       COUNT(*) as trips, " +
                                                        "       ROUND(SUM(price), 2) as total_revenue " +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY start_station, end_station " +
                                                        "ORDER BY total_revenue DESC " +
                                                        "LIMIT 5");
                        profitableRoute.show(false);

                        System.out.println("\nAverage price by duration category:");
                        Dataset<Row> priceByDuration = spark.sql(
                                        "SELECT " +
                                                        "  CASE " +
                                                        "    WHEN duration_minutes <= 15 THEN 'Short (0-15 min)' " +
                                                        "    WHEN duration_minutes <= 30 THEN 'Medium (16-30 min)' " +
                                                        "    WHEN duration_minutes <= 60 THEN 'Long (31-60 min)' " +
                                                        "    ELSE 'Very Long (60+ min)' " +
                                                        "  END as duration_category, " +
                                                        "  COUNT(*) as rental_count, " +
                                                        "  ROUND(AVG(price), 2) as avg_price " +
                                                        "FROM bike_rentals_view " +
                                                        "GROUP BY " +
                                                        "  CASE " +
                                                        "    WHEN duration_minutes <= 15 THEN 'Short (0-15 min)' " +
                                                        "    WHEN duration_minutes <= 30 THEN 'Medium (16-30 min)' " +
                                                        "    WHEN duration_minutes <= 60 THEN 'Long (31-60 min)' " +
                                                        "    ELSE 'Very Long (60+ min)' " +
                                                        "  END " +
                                                        "ORDER BY avg_price");
                        priceByDuration.show(false);

                        System.out.println("\n========================================");
                        System.out.println("ANALYSIS COMPLETE!");
                        System.out.println("========================================\n");

                } catch (Exception e) {
                        System.err.println("\nERREUR lors de l'exécution:");
                        System.err.println("Message: " + e.getMessage());
                        e.printStackTrace();
                } finally {
                        // Stop Spark session
                        spark.stop();
                }
        }
}

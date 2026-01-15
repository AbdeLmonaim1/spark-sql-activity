# 🚴‍♂️ Bike Sharing Analysis - Apache Spark SQL Activity

<div align="center">

![Apache Spark](https://img.shields.io/badge/Apache%20Spark-3.5.1-E25A1C?style=for-the-badge&logo=apache-spark&logoColor=white)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)

**A comprehensive hands-on project demonstrating Apache Spark SQL capabilities through bike rental data analysis**

[Features](#-features) • [Technologies](#-technologies-used) • [Getting Started](#-getting-started) • [Usage](#-usage) • [Exercises](#-exercises-covered) • [Troubleshooting](#-troubleshooting)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [Usage](#-usage)
- [Exercises Covered](#-exercises-covered)
- [Sample Output](#-sample-output)
- [Troubleshooting](#-troubleshooting)
- [Learning Outcomes](#-learning-outcomes)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Overview

This project is a **practical Big Data analytics activity** that demonstrates the power of **Apache Spark SQL** for analyzing bike-sharing rental data. It covers essential Spark SQL operations including data loading, temporary views, SQL queries, aggregations, time-based analysis, and user behavior insights.

The project simulates a real-world scenario where a bike-sharing company wants to analyze rental patterns, user demographics, peak hours, popular routes, and revenue metrics to make data-driven business decisions.

---

## ✨ Features

- 🔄 **Automated Data Generation** - Generate realistic bike rental datasets with customizable size
- 📊 **Comprehensive SQL Analysis** - 6 major exercise categories covering different aspects of data analysis
- ⏰ **Time-Based Insights** - Peak hour analysis and temporal pattern detection
- 👥 **User Behavior Analytics** - Demographic analysis by age groups and gender
- 💰 **Revenue Analysis** - Profitability metrics and pricing patterns
- 🗺️ **Route Analysis** - Popular stations and profitable routes identification
- 🎨 **Clean Console Output** - Well-formatted results with clear section headers

---

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| **Apache Spark** | `3.5.1` | Distributed data processing engine |
| **Spark SQL** | `3.5.1` | SQL query engine for structured data |
| **Java** | `21` | Primary programming language |
| **Maven** | `3.9+` | Dependency management and build tool |
| **SLF4J** | `2.0.9` | Logging framework |
| **Scala** | `2.13` | Spark's underlying language (runtime) |

### Key Spark Components

- **SparkSession** - Entry point for Spark SQL functionality
- **Dataset<Row>** - Distributed collection of data organized into named columns
- **Temporary Views** - In-memory SQL tables for query execution
- **DataFrame API** - High-level API for structured data manipulation

---

## 📁 Project Structure

```
spark-sql-activity/
│
├── .mvn/
│   └── jvm.config                    # JVM arguments for Java 21 compatibility
│
├── src/
│   └── main/
│       └── java/
│           └── ma/
│               └── enset/
│                   ├── BikeRentalAnalysis.java    # Main analysis application
│                   └── data/
│                       └── DataGenerator.java     # CSV data generator
│
├── bike_sharing.csv                  # Generated sample data (1000 records)
├── pom.xml                           # Maven configuration
└── README.md                         # This file
```

---

## 📦 Prerequisites

Before running this project, ensure you have:

- ✅ **Java Development Kit (JDK) 21** or **JDK 17** installed
- ✅ **Apache Maven 3.9+** installed
- ✅ **Minimum 4GB RAM** (recommended for Spark)
- ✅ **Windows 10/11**, **Linux**, or **macOS**

### Verify Installation

```bash
# Check Java version
java -version

# Check Maven version
mvn -version
```

---

## 🚀 Getting Started

### Step 1: Clone or Download the Project

```bash
cd e:\D\Master_SDIA\S3\Big_Data\spark-sql-activity
```

### Step 2: Generate Sample Data

First, generate the bike rental dataset:

```bash
mvn clean compile exec:java -Dexec.mainClass="ma.enset.data.DataGenerator"
```

This creates `bike_sharing.csv` with **1000 sample records** containing:
- Rental ID, User ID, Age, Gender
- Start/End timestamps
- Start/End stations
- Duration and Price

### Step 3: Run the Analysis

Execute the main analysis application:

```bash
mvn clean compile exec:java -Dexec.mainClass="ma.enset.BikeRentalAnalysis" -Dexec.cleanupDaemonThreads=false
```

---

## 💻 Usage

### Running from IDE (IntelliJ IDEA / Eclipse)

If you're using an IDE, you need to add **JVM arguments** for Java 21 compatibility:

#### IntelliJ IDEA
1. Right-click on `BikeRentalAnalysis.java` → **Modify Run Configuration**
2. Add to **VM options**:
```
--add-opens=java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.lang.invoke=ALL-UNNAMED
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED
--add-opens=java.base/java.io=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=java.base/java.nio=ALL-UNNAMED
--add-opens=java.base/java.util=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED
--add-opens=java.base/sun.nio.ch=ALL-UNNAMED
--add-opens=java.base/sun.nio.cs=ALL-UNNAMED
--add-opens=java.base/sun.security.action=ALL-UNNAMED
--add-opens=java.base/sun.util.calendar=ALL-UNNAMED
```

### Alternative: Use Java 17

To avoid JVM arguments, downgrade to Java 17:

1. Update `pom.xml` lines 12-13 and 45-46:
```xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

2. Configure your IDE to use JDK 17
3. Run normally without special arguments

---

## 📚 Exercises Covered

### 🔹 Exercise 1: Data Loading & Exploration
- Load CSV file with schema inference
- Display DataFrame schema
- Show sample records
- Count total rentals

### 🔹 Exercise 2: Create Temporary View
- Register DataFrame as SQL temporary view
- Verify view accessibility

### 🔹 Exercise 3: Basic SQL Queries
- Filter rentals by duration (`> 30 minutes`)
- Filter by specific station (`Station A`)
- Calculate total revenue using `SUM()` aggregation

### 🔹 Exercise 4: Aggregation Queries
- Count rentals per station with `GROUP BY`
- Calculate average duration per station
- Find station with highest rental count using `ORDER BY` and `LIMIT`

### 🔹 Exercise 5: Time-Based Analysis
- Extract hour from timestamp using `HOUR()` function
- Identify peak rental hours
- Find most popular morning station (7 AM - 12 PM)

### 🔹 Exercise 6: User Behavior Analysis
- Calculate average user age
- Analyze rentals by gender distribution
- Segment users into age groups using `CASE WHEN`

### 🎁 Bonus: Additional Insights
- Most profitable routes (station pairs)
- Average price by duration categories
- Revenue optimization opportunities

---

## 📊 Sample Output

When you run the analysis, you'll see structured output like this:

```
========================================
BIKE SHARING ANALYSIS - SPARK SQL
========================================

--- EXERCISE 1: Data Loading & Exploration ---

1. Loading data from bike_sharing.csv...
Chemin du fichier: E:\D\Master_SDIA\S3\Big_Data\spark-sql-activity\bike_sharing.csv
✓ Data loaded successfully

2. Schema:
root
 |-- rental_id: integer (nullable = true)
 |-- user_id: integer (nullable = true)
 |-- age: integer (nullable = true)
 |-- gender: string (nullable = true)
 |-- start_time: timestamp (nullable = true)
 |-- end_time: timestamp (nullable = true)
 |-- start_station: string (nullable = true)
 |-- end_station: string (nullable = true)
 |-- duration_minutes: integer (nullable = true)
 |-- price: double (nullable = true)

4. Total number of rentals: 1000

--- EXERCISE 5: Time-Based Analysis ---

Top 3 Peak Hours:
+----+------------+
|hour|rental_count|
+----+------------+
|14  |55          |
|8   |52          |
|17  |49          |
+----+------------+
```

---

## 🔧 Troubleshooting

### ❌ Error: `IllegalAccessError: sun.nio.ch.DirectBuffer`

**Cause:** Java 21 module access restrictions

**Solution:** Use the Maven command with proper configuration (already set up in `.mvn/jvm.config`):
```bash
mvn clean compile exec:java -Dexec.mainClass="ma.enset.BikeRentalAnalysis" -Dexec.cleanupDaemonThreads=false
```

### ❌ Error: `Unable to resolve table 'bike_rentals_view'`

**Cause:** CSV file not found or empty

**Solution:** 
1. Run `DataGenerator` first to create the CSV
2. Verify `bike_sharing.csv` exists in the project root
3. Check file is not empty (should be ~80KB for 1000 records)

### ❌ Error: `HADOOP_HOME is unset`

**Cause:** Windows-specific Hadoop warning (harmless)

**Solution:** This is just a warning and doesn't affect functionality. You can safely ignore it.

### ❌ Maven Build Fails

**Solution:**
```bash
# Clean and rebuild
mvn clean install -U

# If still failing, check Maven settings
mvn -version
```

---

## 🎓 Learning Outcomes

By completing this activity, you will learn:

- ✅ How to initialize and configure a **SparkSession**
- ✅ Loading and exploring data with **Spark DataFrames**
- ✅ Creating and using **Temporary SQL Views**
- ✅ Writing **SQL queries** in Spark (SELECT, WHERE, GROUP BY, ORDER BY)
- ✅ Using **aggregate functions** (COUNT, SUM, AVG, ROUND)
- ✅ Working with **timestamp functions** (HOUR, BETWEEN)
- ✅ Implementing **conditional logic** with CASE WHEN
- ✅ Performing **multi-level aggregations** and **window operations**
- ✅ Analyzing **real-world business metrics** with Big Data tools
- ✅ Understanding **Spark SQL optimization** and best practices

---

## 🤝 Contributing

Contributions are welcome! Here are some ideas for enhancements:

- 📈 Add data visualization with Apache Zeppelin or Jupyter
- 🌍 Implement geospatial analysis for station locations
- 🤖 Add machine learning models for demand prediction
- 📊 Create interactive dashboards with Spark + Tableau
- 🔄 Implement streaming analysis with Spark Structured Streaming

---

## 📄 License

This project is licensed under the **MIT License** - feel free to use it for educational purposes.

---

## 👨‍💻 Author

**Master SDIA - Big Data Course**  
*Semester 3 - Spark SQL Activity*

---

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Troubleshooting](#-troubleshooting) section
2. Review [Apache Spark Documentation](https://spark.apache.org/docs/3.5.1/)
3. Consult [Spark SQL Guide](https://spark.apache.org/docs/3.5.1/sql-programming-guide.html)

---

<div align="center">

### ⭐ If you found this project helpful, please give it a star!

**Made with ❤️ using Apache Spark**

</div>

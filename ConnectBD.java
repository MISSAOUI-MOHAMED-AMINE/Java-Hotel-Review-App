import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
class Connect {
    private static Connection con;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return con;
    }
}
public class ConnectBD{
    private Connection con=Connect.getConnection();

    public ResultSet getAllInformation() throws SQLException {
        String query = "SELECT * FROM review";
        Statement statement = con.createStatement();
        return statement.executeQuery(query);
    }


    public Map<Integer, Double> getAverageRating() {
        Map<Integer, Double> result = new HashMap<>();
        try {
            // Query to retrieve ratings and dates from the review table
            String query = "SELECT rating, date FROM review";

            try (PreparedStatement statement = con.prepareStatement(query)) {

                try (ResultSet resultSet = statement.executeQuery()) {
                    Map<Integer, Double[]> yearRatingMap = new HashMap<>();

                    while (resultSet.next()) {
                        float rating = resultSet.getFloat("rating");
                        int year = resultSet.getInt("date");

                        // Check if the year is already in the map
                        if (!yearRatingMap.containsKey(year)) {
                            // If not, add a new entry with an array [sum, count]
                            yearRatingMap.put(year, new Double[]{0.0, 0.0});
                        }

                        // Update the sum and count in the array
                        Double[] values = yearRatingMap.get(year);
                        values[0] += rating; // sum
                        values[1] += 1.0;    // count
                    }

                    // Calculate the average for each year and put it in the result map
                    for (Map.Entry<Integer, Double[]> entry : yearRatingMap.entrySet()) {
                        int year = entry.getKey();
                        Double[] values = entry.getValue();
                        double average = values[0] / values[1];
                        result.put(year, average);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<Integer, Integer> getDataByYear(String feature, int year) {
        Map<Integer, Integer> result = new HashMap<>();
        int x=0;
        try {
            // First, fill the map with all possible ratings and initialize counts to 0
            for (int i = 1; i <= 5; i++) {
                result.put(i, 0);
            }
            // Update counts with actual data for the given feature and year
            String query = "SELECT " + feature + ", COUNT(*) as count FROM review WHERE date = ? GROUP BY " + feature;
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, year);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int rating = resultSet.getInt(feature);
                        int count = resultSet.getInt("count");
                        result.put(rating, count);
                        x++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (x==0) result=new HashMap<>();
        return result;
    }
    
    public boolean checkID(int x){
        boolean test = false;
        try{ 
        String query = "SELECT room FROM review WHERE room = "+x;
        PreparedStatement prepStat = con.prepareStatement(query);
        ResultSet resultSet = prepStat.executeQuery();
        if(resultSet.next()){
            test=true;
        }
        } catch (SQLException er) {
            er.printStackTrace();
        
    }
        return test;
    }

    public void save(int a,int b,int c,int d,int e,int f,int g,float h,String x){
        try{ 
        String query = "INSERT INTO review (room,security,comfort,proprety,animation,food,service,rating,comment,date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStat = con.prepareStatement(query);
        prepStat.setInt(1, a);
        prepStat.setInt(2, b);
        prepStat.setInt(3, c);
        prepStat.setInt(4, d);
        prepStat.setInt(5, e);
        prepStat.setInt(6, f);
        prepStat.setInt(7, g);
        prepStat.setFloat(8, h);
        prepStat.setString(9, x);
        prepStat.setInt(10, Calendar.getInstance().get(Calendar.YEAR));
        prepStat.executeUpdate();
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }


   
}

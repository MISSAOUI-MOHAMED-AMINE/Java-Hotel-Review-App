import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GraphicPage extends JFrame {
    private ConnectBD connectBD;
    private JComboBox<String> featureComboBox;
    private JTextField yearTextField;

    public GraphicPage() {
        super("Graphical Page");
        connectBD = new ConnectBD();

        // Create GUI components
        featureComboBox = new JComboBox<>(new String[]{"security", "comfort", "proprety", "animation", "food", "service"});
        yearTextField = new JTextField(4);
        setIconImage(new ImageIcon("C:\\Users\\msi\\Desktop\\Nouveau dossier (4)\\logo.jpg").getImage());
        JButton generateButton = new JButton("Generate Chart");
        generateButton.addActionListener(new GenerateButtonListener());

        JButton displayAllButton = new JButton("Display All Information");
        displayAllButton.addActionListener(new DisplayAllButtonListener());

        JButton displayChartButton = new JButton("Display General Rating Chart");
        displayChartButton.addActionListener(new DisplayChartButtonListener());

        // Set up the layout
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Select Feature:"));
        panel.add(featureComboBox);
        panel.add(new JLabel("Year:"));
        panel.add(yearTextField);
        JPanel pan=new JPanel(new FlowLayout());
        JPanel pans=new JPanel(new FlowLayout());
        panel.add(generateButton);
        pan.add(displayAllButton);  // Add the new button
        pans.add(displayChartButton);
        // Set up the frame
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(pan, BorderLayout.CENTER);
        mainPanel.add(pans, BorderLayout.SOUTH);
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class DisplayChartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Map<Integer, Double> averageRatings = connectBD.getAverageRating();

                if (!averageRatings.isEmpty()) {
                    // Create dataset
                    CategoryDataset dataset = createDataset(averageRatings);

                    // Create chart
                    JFreeChart chart = ChartFactory.createLineChart(
                            "Average Rating for Each Year",
                            "Year",
                            "Average Rating",
                            dataset,
                            PlotOrientation.VERTICAL,
                            true, true, false
                    );

                    // Create chart panel
                    chart.getCategoryPlot().getRenderer().setSeriesStroke(0, new BasicStroke(3.0f));
                    ChartPanel chartPanel = new ChartPanel(chart);
                    chartPanel.setPreferredSize(new Dimension(800, 600));

                    // Display chart in a new window
                    JFrame chartFrame = new JFrame("Chart");
                    chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    chartFrame.getContentPane().add(chartPanel);
                    chartFrame.pack();
                    chartFrame.setLocationRelativeTo(null);
                    chartFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No data available.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error getting average ratings.");
            }
        }
    }

    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedFeature = (String) featureComboBox.getSelectedItem();
            String yearString = yearTextField.getText();

            if (!yearString.isEmpty()) {
                try {
                    int selectedYear = Integer.parseInt(yearString);
                    Map<Integer, Integer> data = connectBD.getDataByYear(selectedFeature, selectedYear);

                    if (!data.isEmpty()) {
                        // Create dataset
                        CategoryDataset dataset = createDataset(data);

                        // Create chart
                        JFreeChart chart = ChartFactory.createBarChart(
                                "Number of Occurrences of each rating for " + selectedFeature + " in " + selectedYear,
                                selectedFeature,
                                "Occurrences",
                                dataset,
                                PlotOrientation.VERTICAL,
                                true, true, false
                        );

                        // Create chart panel
                        ChartPanel chartPanel = new ChartPanel(chart);
                        chartPanel.setPreferredSize(new Dimension(800, 600));
                                setIconImage(new ImageIcon("C:\\Users\\msi\\Desktop\\Nouveau dossier (4)\\logo.jpg").getImage());

                        // Display chart in a new window
                        JFrame chartFrame = new JFrame("Chart");
                        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        chartFrame.getContentPane().add(chartPanel);
                        chartFrame.pack();
                        chartFrame.setLocationRelativeTo(null);
                        chartFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "No data available for the selected feature and year.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid year.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid year.");
            }
        }

        private CategoryDataset createDataset(Map<Integer, Integer> data) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
                dataset.addValue(entry.getValue(), "Occurrences", String.valueOf(entry.getKey()));
            }

            return dataset;
        }
    }

    private class DisplayAllButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayAllInformation();
        }

        private void displayAllInformation() {
            try {
                // Retrieve all information from the database
                ResultSet resultSet = connectBD.getAllInformation();
        
                // Get column names
                Vector<String> columnNames = getColumnNames(resultSet);
        
                // Get data
                Vector<Vector<Object>> data = getData(resultSet);
        
                // Create JTable
                JTable table = new JTable(data, columnNames);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
                // Create a scroll pane for the table
                JScrollPane scrollPane = new JScrollPane(table);
                
                // Show the information in a new window
                JFrame infoFrame = new JFrame("All Information");
                
                infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                infoFrame.getContentPane().add(scrollPane);
                infoFrame.setSize(750, 600);
                setIconImage(new ImageIcon("C:\\Users\\msi\\Desktop\\Nouveau dossier (4)\\logo.jpg").getImage());

                infoFrame.setLocationRelativeTo(null); // Center the frame on the screen
                infoFrame.setVisible(true);
        
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error retrieving information from the database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        

        private Vector<String> getColumnNames(ResultSet resultSet) throws SQLException {
            // Get column count
            int columnCount = resultSet.getMetaData().getColumnCount();

            Vector<String> columnNames = new Vector<>();

            // Get column names
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }

            return columnNames;
        }

        private Vector<Vector<Object>> getData(ResultSet resultSet) throws SQLException {
            // Create vector for data
            Vector<Vector<Object>> data = new Vector<>();
        
            // Get column count
            int columnCount = resultSet.getMetaData().getColumnCount();
        
            // Process each row
            while (resultSet.next()) {
                // Create vector for each row
                Vector<Object> row = new Vector<>();
        
                // Get data for each column
                for (int i = 1; i <= columnCount; i++) {
                    if (resultSet.getMetaData().getColumnName(i).equalsIgnoreCase("date")) {
                        // If the column is "date", extract the int value
                        int year = resultSet.getInt(i);
                        row.add(year);
                    } else {
                        // For other columns, add the data as is
                        row.add(resultSet.getObject(i));
                    }
                }
        
                // Add the row to the data vector
                data.add(row);
            }
        
            return data;
        }
        
        
    }

    

        private CategoryDataset createDataset(Map<Integer, Double> data) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (Map.Entry<Integer, Double> entry : data.entrySet()) {
                dataset.addValue(entry.getValue(), "Average Rating", String.valueOf(entry.getKey()));
            }

            return dataset;
        }
     
        public static void main(String[] args) {
            new GraphicPage();
            
        }
    }



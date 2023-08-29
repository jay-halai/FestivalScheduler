import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


/**
  * This class represents the GUI of the program and ulitmately is where all the functions 
  * and methods are called to be executed.
**/
public class SchedulerGUI implements ActionListener {

    private JFrame mainFrame;
    private JFrame targetTimeFrame;
    private LocalTime targetTime;
    private JPanel inputLabelPanel;
    private JPanel inputTxtPanel;
    private JTextField[] txtFieldArray = new JTextField[4]; // array of colour buttons at the bottom of GUI
    private JLabel[] lblArray = new JLabel[5]; // array of colour buttons at the bottom of GUI
    private JButton[] btnArray = new JButton[5]; // array of colour buttons at the bottom of GUI
    private JPanel btnPanel;
    private JTable scheduleTable;
    private DefaultTableModel scheduleTableModel;
    private ArrayList<Act> actList = new ArrayList<>();
    private String[] priority = new String[100];
    private JComboBox<String> optionActPriority;
    private JPanel schedulePanel;
    private int actID;
    private String actName;
    private int actDuration;
    private int actPriority = 1;
    private LocalTime startTime;
    private String removePriority;
    private int buttonIncrement = 0;
    private int lblIncrement = 0;
    private boolean uniqueActID = true;
    Object[][] data = new Object[100][100];
    String columnNames[] = { "Act ID", "Name", "Duration", "Priority", "Start Time" };

    /**
      *The createLabel function which creates all the labels and adds them to an array
      *@param name so I can set the text displayed in the program
     **/
    public void createLabel(String name) {
        JLabel label = new JLabel();
        label.setText(name);
        label.setForeground(Color.WHITE);
        inputLabelPanel.add(label);
        lblArray[lblIncrement] = label;
        lblIncrement++;
    }

    /**
      *The createTextFields function which creates all the Textfields and adds them to an array
      *Will then be accessed via index later in the program
     **/
    public void createTextFields() {
        for (int i = 0; i < 4; i++) {
            JTextField txtField = new JTextField();
            inputTxtPanel.add(txtField);
            txtFieldArray[i] = txtField;
        }
    }

    /**
      *The createButton function which creates all the Buttons and adds them to an array
      *Adds actionlistener so know when an action is performed on the object
      *Will then be accessed via index later in the program
      *@param name so I can set the name of the button
     **/
    public void createButton(String name) {
        JButton button = new JButton();
        button.addActionListener(this);
        button.setText(name);
        btnPanel.add(button);
        btnArray[buttonIncrement] = button;
        buttonIncrement++;
    }

    /**
      *function to add integers to the priority array to insert into the combobox for users to select Priority
     **/
    public void addPriorities() {
        int j = 0;
        for (int i = 0; i < 100; i++) {
            j++;
            priority[i] = String.valueOf(j);
        }
    }

    /**
     * Constructor which creates the GUI and ulitamately where most things are ran
     **/
    public SchedulerGUI() {

        Color myColour = Color.decode("#3452eb"); // sets the background colour I want to myColour
        JOptionPane.showMessageDialog(mainFrame, "Welcome to the Scheduler! Plese enter Headline Act!");

        // -------------------------------COMPONENTS FOR MAIN FRAME----------------------------------------

        mainFrame = new JFrame("Scheduler");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);
        mainFrame.setSize(1000, 600);
        mainFrame.setResizable(false);

        // -------------------------------COMPONENTS FOR INPUT LABEL PANEL----------------------------------------

        inputLabelPanel = new JPanel();
        inputLabelPanel.setLayout(new GridLayout(7, 1));
        inputLabelPanel.setBackground(myColour);
        inputLabelPanel.setBounds(0, 0, 100, 600);
        mainFrame.add(inputLabelPanel);

        createLabel("Act ID: ");
        createLabel("Act Name: ");
        createLabel("Act Duration: ");
        createLabel("Target Time: ");
        createLabel("Act Priority: ");

        // -------------------------------COMPONENTS FOR INPUT TEXT PANEL----------------------------------------

        inputTxtPanel = new JPanel();
        inputTxtPanel.setLayout(new GridLayout(7, 1));
        inputTxtPanel.setBackground(myColour);
        inputTxtPanel.setBounds(100, 0, 100, 600);
        mainFrame.add(inputTxtPanel);

        createTextFields();

        addPriorities();
        optionActPriority = new JComboBox(priority);
        optionActPriority.addActionListener(this);
        optionActPriority.setEnabled(false);
        inputTxtPanel.add(optionActPriority);

        // -------------------------------COMPONENTS FOR BUTTON PANEL----------------------------------------

        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setBackground(myColour);
        btnPanel.setBounds(200, 400, 800, 200);
        mainFrame.add(btnPanel);

        createButton("Add Act");
        createButton("Delete Act");
        createButton("View Schedule");

        // -------------------------------COMPONENTS FOR SCHEDULE PANEL----------------------------------------

        schedulePanel = new JPanel();
        schedulePanel.setLayout(new FlowLayout());
        schedulePanel.setBackground(myColour);
        schedulePanel.setBounds(200, 0, 800, 400);
        mainFrame.add(schedulePanel);

        scheduleTableModel = new DefaultTableModel(data, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        scheduleTable = new JTable(scheduleTableModel);
        JScrollPane sp = new JScrollPane(scheduleTable);
        sp.setPreferredSize(new Dimension(800, 400));
        schedulePanel.add(sp);

        mainFrame.setVisible(true);
    }

    /**
      *Function to add the schedule to the table, clearing the previous data and switching with new contents of actList ArrayList
      *Called each time user clicks 'View Schedule' Button.
     **/    
    public void viewSchedule() {

        for(int i=0; i< 100;i++)
        {
            for(int j=0; j<100;j++)
            {
                data[i][j] = null;
            }
        }

        for (int i = 0; i < actList.size(); i++) {

            data[i][0] = actList.get(i).getActID();
            data[i][1] = actList.get(i).getActName();
            data[i][2] = actList.get(i).getActLength();
            data[i][3] = actList.get(i).getActPriority();
            data[i][4] = actList.get(i).getStartTime();
        }
        DefaultTableModel newTable = new DefaultTableModel(data, columnNames){
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scheduleTable.setModel(newTable);
    }

    /**
      *Algorithm to Sort the Schedule. First use bubble sort to sort list by priority in ascending order.
      *Algorithm updates the current before and after start time times for the length of the array list
      *Sets the start time of current act to before start time if difference is less than after and vice versa.
     **/ 
    public void sortSchedule() {
        Act temp;
        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < actList.size() - 1; i++) {
                if (actList.get(i).compareTo(actList.get(i + 1)) > 0) {
                    temp = actList.get(i);
                    actList.set(i, actList.get(i + 1));
                    actList.set(i + 1, temp);
                    sorted = false;
                }
            }
        }

        long aDur = actList.get(0).getActLength();
        long aDiff;
        long bDiff;

        int a = 1;
        int b = 1;

        LocalTime aST = actList.get(0).getStartTime();
        LocalTime bST = actList.get(0).getStartTime();

        for (int i = 1; i < actList.size(); i++) {
            aST = aST.plusMinutes(aDur);
            bST = bST.minusMinutes(actList.get(i).getActLength());
            aDiff = ChronoUnit.MINUTES.between(actList.get(0).getStartTime(), aST);
            bDiff = ChronoUnit.MINUTES.between(bST, actList.get(0).getStartTime());

            if (bDiff < aDiff) {
                actList.get(i).setStartTime(bST);
                aST = actList.get(i - a).getStartTime();
                a++;
                aDur = actList.get(i - 1).getActLength();
            }

            else {
                actList.get(i).setStartTime(aST);
                bST = actList.get(i - b).getStartTime();
                b++;
                aDur = actList.get(i).getActLength();
            }
        }
    }


    /**
     * Action Performed method. Takes input from each field, as well as validates, when add act button is clicked.
     * Also method where act is added to the arraylist after all validations are complete.
     *
     *@param e When an action occurs it is saved to e.
     **/
    public void actionPerformed(ActionEvent e) {

        //once 1 is selected in the combo box the entry for the target time will appear else it will disappear
        if (e.getSource() == optionActPriority) {
            if (Integer.parseInt(optionActPriority.getSelectedItem().toString()) != 1) {
                txtFieldArray[3].setVisible(false);
                lblArray[3].setVisible(false);
            }

            else {
                txtFieldArray[3].setVisible(true);
                lblArray[3].setVisible(true);
            }
        }

        if (e.getSource() == btnArray[0]) {
            
            //Validation check for Act ID
            uniqueActID = true;
            if ((txtFieldArray[0].getText().isEmpty() == false)) {
                try {
                    actID = Integer.parseInt(txtFieldArray[0].getText());
                    for (Act a : actList) {
                        if (a.getActID() == actID) {
                            uniqueActID = false;
                            JOptionPane.showMessageDialog(mainFrame, "Sorry, Act ID already exists");
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Sorry, please enter an integer for ID");
                }
            }

            else {
                JOptionPane.showMessageDialog(mainFrame, "Sorry, please enter Act ID");
                actID = -1;
            }

            //Null check for Act Name
            if ((txtFieldArray[1].getText().isEmpty() == false)) {
                actName = txtFieldArray[1].getText();
            }

            else {
                JOptionPane.showMessageDialog(mainFrame, "Sorry, please enter Act Name");
                actName = null;
            }

            //Validation check for act Duration and setting startTime to "00:00" each time
            if ((txtFieldArray[2].getText().isEmpty() == false)) {

                try {
                    actDuration = Integer.parseInt(txtFieldArray[2].getText());
                    actPriority = Integer.parseInt(optionActPriority.getSelectedItem().toString());
                    startTime = LocalTime.parse("00:00", DateTimeFormatter.ofPattern("HH:mm"));
                    removePriority = optionActPriority.getSelectedItem().toString();
                }

                catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Sorry, please enter an integer for Duration");
                }
            }

            else {
                JOptionPane.showMessageDialog(mainFrame, "Sorry, please enter Act Duration");
                actDuration = 0;
            }

            //Validation check for target time entry if priority is 1
            if (actPriority == 1) {

                if ((txtFieldArray[3].getText().isEmpty() == false)) {
                    try {
                        targetTime = LocalTime.parse(txtFieldArray[3].getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    } 
                    
                    catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(targetTimeFrame,"Sorry, please enter Target Time in format of HH:mm");
                    }
                }

                else {
                    JOptionPane.showMessageDialog(targetTimeFrame, "Sorry, please enter Target Time");
                }
            }

            //If none of the variables are null then create act object and add to array list.
            if ((actID != -1) && (actName != null) && (actDuration != 0) && (actPriority != 0) && (uniqueActID == true) && (targetTime != null)) {
                
                if (actPriority == 1) {
                    startTime = targetTime;
                }
                optionActPriority.removeItem(removePriority);
                Act newAct = new Act(actID, actName, actDuration, actPriority, startTime);
                txtFieldArray[0].setText("");
                txtFieldArray[1].setText("");
                txtFieldArray[2].setText("");
                txtFieldArray[3].setText("");
                actList.toString();
                actList.add(newAct);
                JOptionPane.showMessageDialog(mainFrame, "Act Successfully Added!");
                optionActPriority.setEnabled(true);
                actID = 0;
                actDuration = 0;
                actPriority = 0;
                actName = "";
            }
        }

        //If delete button is clicked iterate through list until actID = actID and delete Act at the location
        if (e.getSource() == btnArray[1]) {
            
            if ((txtFieldArray[0].getText().isEmpty() == false)) {
                try {
                    actID = Integer.parseInt(txtFieldArray[0].getText());

                    Iterator<Act> iter = actList.iterator();

                    while (iter.hasNext()) {
                        Act act = iter.next();
                    
                        if (actID == act.getActID())
                        {
                            iter.remove();
                            JOptionPane.showMessageDialog(mainFrame, "Act Successfully Deleted");
                            optionActPriority.addItem(String.valueOf(act.getActPriority()));
                        }
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Sorry, please enter an integer for ID");
                }
            }

            else {
                JOptionPane.showMessageDialog(mainFrame, "Sorry, please enter Act ID");
            }

        }

        //If view schedule button is clicked then sort the schedule and display in the table.
        if (e.getSource() == btnArray[2]) {
            sortSchedule();
            viewSchedule();
        }
    }
}

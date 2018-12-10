import org.eclipse.jgit.api.errors.GitAPIException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.Exception;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import java.sql.*;

/*
Inside DB
Url     char
Lines   char
Words   char
Characters   char
SourceLines char
CommentLines char

 */



class Database  {
    Connection conn;
    //Statement st;
    PreparedStatement upDateAll = null;
    PreparedStatement updateTotal = null;



    public Database() throws SQLException , ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");//.newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FileDB?useSSL=false", "root", "SJ9Qwq27md9XcpK");


        System.out.println("Connected to the MySQL database");
        //st = conn.createStatement();/*Creating Statement Class's object which is responsible for performing all db tasks*/
        //PreparedStatement st = conn.prepareStatement();
         String sql = "INSERT INTO Files(Lines,Words,Characters,SourceLines,CommentLines," +
               "totSourcetrack,totalOperators,totalOperands)" + "VALUES (?,?,?,?,?)";
    }


    /*

      // the mysql insert statement
      String query = " insert into users (first_name, last_name, date_created, is_admin, num_points)"
        + " values (?, ?, ?, ?, ?)";

      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = conn.prepareStatement();
     */







    public void fetchData() throws Exception
    {
        String query = "SELECT * FROM Files";
        //ResultSet rs = st.executeQuery(query);


        /*used to verify database returned values
        while(rs.next())
        {
            System.out.println("Name : "+rs.getString("name"));
            System.out.println("age : "+rs.getInt("age"));
        }
        */

    }


//    public void insertUrl(String Url ) throws SQLException
  //  {
    //    String sql = "INSERT INTO Files(Url)" + "VALUES ('"+Url+"')";

        //st.executeUpdate(sql);



       /*



        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        // preparedStatement.setString(1, nameOfFile);
        preparedStatement.setString(1, Url);

        // String query = "insert into user values(\""+name+"\","+age+")";
        // int a = stObj.executeUpdate(query);
        */

   // }




    public void insertData(String Url,String Lines,String Words, String Characters,String SourceLines, String CommentLines ) throws SQLException
    {

        System.out.println("database");
        //updateSales = con.prepareStatement(updateString);
       // String sql = "INSERT INTO Files(Lines,Words,Characters,SourceLines,CommentLines," +
         //       "totSourcetrack,totalOperators,totalOperands)" + "VALUES (?,?,?,?,?)"
        String sql = "INSERT INTO Files(Url,Lines,Words,Characters,SourceLines,CommentLines," +
                "totSourcetrack,totalOperators,totalOperands)" + "VALUES (?,?,?,?,?,?)";

        //String sql = "INSERT INTO Files VALUE (\""+u+"\",\""+Lines+"\",\""+Words+"\",\""+Characters+"\",\""+SourceLines+"\",\""+CommentLines+"\")";


        //String sql = "INSERT INTO `Files`(Url,Lines,Words,Characters,SourceLines,CommentLines) VALUE ('"+u+"','"+Lines+"','"+Words+"','"+Characters+"','"+SourceLines+"'','"+CommentLines+"')";

        //s.executeUpdate("INSERT INTO `time_entry`(pid,tid,rid,tspend,description) VALUE ('"+pid+"','"+tid+"','"+rid+"',"+tspent+",'"+des+"')");

        updateTotal = conn.prepareStatement(sql);

        //st.execute(sql);



        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, Url);
        preparedStatement.setString(2, Lines);
        preparedStatement.setString(3, Words);
        preparedStatement.setString(4, Characters);
        preparedStatement.setString(5, SourceLines);
        preparedStatement.setString(6, CommentLines);

       // String query = "insert into user values(\""+name+"\","+age+")";
       // int a = stObj.executeUpdate(query);

       // st.execute(sql);
        updateTotal.executeUpdate();
    }



    void deleteData(String Url) throws Exception
    {

       /*
        String query = "Delete FROM Files WHERE Files \""+Url+"\"";
        //int a = st.executeUpdate(query);

        //if(a == 1)
        {
            System.out.println("delete Successful");
        }
        else
        {
            System.out.println("deletion Failed");

        }
        */
    }

}




public class UserInterface {
    public static Database d;
    private static JFrame frame;
    private final static String frameName = "Group 6 GitHub Metrics";
    private static JLabel blankLabel = new JLabel("");



    static
    {
        try{
            d = new Database();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    // START TEST CODE //////////////////////////////////////
    public static void main(String[] args)
    {

        run();
        //String databaseName= "";






        //will call GUI to display contents of the DB
        //for now will comment it out until merging of files is
        //successful

       // new DisplayQuery();


    }
    // END TEST CODE ////////////////////////////////////////


    /**
     * This will intake the database of git repos
     * Then it will run the JFrames
     */
    public static void run(){
        try {
            init();
        } catch (Exception e) {
            // catch works on init exceptions, but doesn't catch entire program?
            handleError(e);
        }
    }

    /**
     * UTILITY FUNCTION
     *
     * This function will be used any time the frame needs to be wiped
     * it remakes a new frame in the exact position of the last one.
     * This is the case even if the user manually moves the frame.
     */
    private static void makeEmptyFrame() {
        // initialize location
        Point location = new Point();
        Rectangle size = new Rectangle(); // can be used to base new frame off previous frame size
        boolean existingFrame = false;

        // check if frame already exists
        if (frame != null) {
            existingFrame = true;

            // get old frame's location (if exists, otherwise null?)
            location = frame.getLocation();
            size = frame.getBounds(); // can be used to base new frame off previous frame size

            frame.dispose();
        }

        frame = new JFrame(frameName); // name can be changed later. Or this is fine.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (existingFrame) {
            // set location to the same as previous location
            frame.setLocation((int) location.getX(), (int) location.getY());
        } else {
            // set location to center screen
            setFrameCentered();
        }

    }

    /**
     * UTILITY FUNCTION
     *
     * This will set the frame to the center of the user's screen.
     */
    private static void setFrameCentered(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    /**
     * This function creates the initial frame
     * it will consist of two buttons only
     * one will allow the user to input a new github repo
     * the other will allow the user to select between repos that they already have used
     */
    private static void init() throws Exception{
        makeEmptyFrame();

        // create buttons
        // ask user if they want to add new repo
        JButton newRepo = new JButton("Add new repo");
        frame.add(newRepo);
        JButton oldRepoList = new JButton("List of Repo Metrics");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.add(oldRepoList);

        // add newRepo button listener
        newRepo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addNewRepo();
            }
        });

        // go straight to previous list
        oldRepoList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                // add text box repo to database

                repoListBox();
            }
        });

        frame.pack(); // sizes window to fit everything
        frame.setVisible(true); // display
    }

    /**
     * resets frame to allow user to add new github repo to the database.
     * will then go to list of repos to get metrics.
     */
    private static void addNewRepo() {
        makeEmptyFrame();

        // create text field for github repo and button
        JLabel label = new JLabel("Enter GitHub repo URL:");
        JTextField repoInputField = new JTextField(20);
        JButton addRepoButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        // add frame elements
        JPanel panel = new JPanel();
        // set layout as a grid of width 2, height variable
        panel.setLayout(new GridLayout(0, 2));
        frame.add(panel);

        // add text box and button
        panel.add(label);
        panel.add(repoInputField);
        panel.add(addRepoButton);
        panel.add(cancelButton);

        frame.pack();
        frame.setVisible(true); // display

        addRepoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // add text box repo to database
                /*
                    NEED TO ACCESS AND ADD TO REPO DATABASE
                 */

                repoListBox();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                repoListBox();
            }
        });
    }

    /**
     * takes in URL for repo and deletes it from database
     *
     * @param repoAddress
     */
    private static void deleteRepo(String repoAddress){
        makeEmptyFrame();

        JLabel header = new JLabel("<html>&nbsp;Would you like to delete the following repo?&nbsp;&nbsp;</html>");
        JLabel repoAddressLabel = new JLabel("<html><li>" + repoAddress + "&nbsp;&nbsp;</html>");
        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        frame.add(panel);

        panel.add(header);
        panel.add(repoAddressLabel);
        panel.add(deleteButton);
        panel.add(cancelButton);

        frame.pack();
        frame.setVisible(true);

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // delete repoAddress from repo database
                /*
                    NEED TO ACCESS AND DELETE FROM REPO DATABASE
                 */

                repoListBox();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                repoListBox();
            }
        });
    }

    /**
     * this is the main frame. It will display a drop down list
     * of the repos. It will also display metrics and a button
     * that allows the user to refresh the metrics
     */
    private static void repoListBox(){
        makeEmptyFrame();

        // drop down list of repos
        /*
            NEED TO ACCESS OLD REPOS
            reposArray will be filled from database
         */
        String[] reposArray = {"https://github.com/Jovanyp23/WordCountProject", "https://github.com/CSC131Fall2018/Group6", "repo 3"};




        JComboBox<String> repoDropdownList = new JComboBox<String>(reposArray);

        // button to run metrics
        JButton runMetricsButton = new JButton("Run Metrics");
        // button to go back to addNewRepo()
        JButton addNewRepoButton = new JButton("Add New Repo");
        // button to delete this repo
        JButton deleteRepoButton = new JButton("Delete This Repo");
        // button to go to history
        JButton historyRepoButton = new JButton("Get Repo History");

        // metrics
        // metrics labels
        JLabel linesHeader = new JLabel("Lines");
        JLabel wordsHeader = new JLabel("Words");
        JLabel charsHeader = new JLabel("Characters");
        JLabel sourceHeader = new JLabel("Source Lines");
        JLabel commentHeader = new JLabel("Comment Lines");

        // metrics numbers
        JLabel lines = new JLabel("-");
        JLabel words = new JLabel("-");
        JLabel chars = new JLabel("-");
        JLabel sources = new JLabel("-");
        JLabel comments = new JLabel("-");


        // add frame elements
        JPanel panel = new JPanel();
        // set layout as a grid of width 2, height variable
        panel.setLayout(new GridLayout(0,2));

        // first row
        frame.add(panel);
        panel.add(repoDropdownList);
        panel.add(runMetricsButton);

        //  buttons
        panel.add(addNewRepoButton);
        panel.add(deleteRepoButton);
        panel.add(historyRepoButton);
        panel.add(blankLabel); //blank spot

        // metrics definitions
        panel.add(linesHeader);
        panel.add(wordsHeader);



        panel.add(lines);
        panel.add(words);
        panel.add(charsHeader);
        panel.add(sourceHeader);
        panel.add(chars);
        panel.add(sources);
        panel.add(commentHeader);
        JLabel blankLabel2 = new JLabel("");
        panel.add(blankLabel2);
        panel.add(comments);

        frame.pack();

        frame.setVisible(true);



        // action listeners on buttons
        addNewRepoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                addNewRepo();
            }
        });

        deleteRepoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               // deleteRepo(repoDropdownList.getSelectedItem().toString());
            }
        });

        runMetricsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // run metrics and update
                /*
                    NEED ACCESS TO METRICS
                    get from metrics.java {
                        lines.setText("12313");
                        words.setText("12341");
                        chars.setText("98384");
                        sources.setText("234");
                        comments.setText("93948");
                    }
                    change each metrics label
                    store into history sql
                 */


                //at reposArray
                //will add dummy repo here to test values
                String Url= "u";
                //d.insertUrl(Url);
                retMets temp = new retMets();
                try {
                    ArrayList metrics = temp.getMetrics(repoDropdownList.getSelectedItem().toString());
                    lines.setText(metrics.get(2).toString());
                    words.setText(metrics.get(3).toString());
                    chars.setText(metrics.get(4).toString());
                    sources.setText(metrics.get(5).toString());
                    comments.setText(metrics.get(6).toString());

                    //String l2 = lines.setText(metrics.get(2).toString());
                String u="1";
                String l="3";
                    String w="4";
                    String c="5";
                    String s="6";
                    String cd="7";

             d.insertData(u,l,w,c,s,cd);
                    // send to sql
                } catch (Exception e) {
                    e.printStackTrace();
                    handleError(e);
                }



            }
        });

        historyRepoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                repoHistoryBox(repoDropdownList.getSelectedItem().toString());

            }
        });

    }

    /**
     * this will take in the repo that is selected in repoListBox()
     * and get a history of progress for the repo's metrics
     *
     * @param repoToGet is from the dropdown in repoListBox()
     */
    private static void repoHistoryBox(String repoToGet){
        makeEmptyFrame();

        // row one, header
        JLabel header = new JLabel("<html>&nbsp;&nbsp;Repo History:&nbsp;&nbsp;</html>");
        JLabel repoLabel = new JLabel("<html>" + repoToGet + "&nbsp;&nbsp;</html>");
        JButton backButton = new JButton("Back");

        // row two, metrics headers
        // date/time, lines, words, chars, source lines, comment lines
        JLabel timestamp = new JLabel("<html>&nbsp;&nbsp;Timestamp</html>");
        JLabel lines = new JLabel("Lines");
        JLabel words = new JLabel("Words");
        JLabel chars = new JLabel("Characters");
        JLabel sources = new JLabel("Source Lines");
        JLabel comments = new JLabel("<html>Comment Lines&nbsp;&nbsp;</html>");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,6));
        frame.add(panel);



        // adding row one
        panel.add(header);
        panel.add(repoLabel);
        for (int i = 0; i < 3; i++) {
            JLabel blankLabelRepeater = new JLabel("");
            panel.add(blankLabelRepeater);
        }
        panel.add(backButton);


        // adding row two
        panel.add(timestamp);
        panel.add(lines);
        panel.add(words);
        panel.add(chars);
        panel.add(sources);
        panel.add(comments);

        // add metrics history
        Map<String, Map> databaseMetrics = new HashMap<String, Map>();
            // GET MAP DATA FROM DATABASE. KEY = TIMESTAMP?
            // MAP = MAP OF METRICS?

        ArrayList<String> keys = new ArrayList(databaseMetrics.keySet());
        for (String key : keys) {

        }

        frame.pack();
        setFrameCentered();
        frame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                repoListBox();
            }
        });
    }



    /**
     * this will take any exceptions and open a jframe
     * that will display the error message to user
     *
     * @param e is the exception that is thrown
     */
    private static void handleError(Exception e){
        // error caught, close everything, make empty frame
        makeEmptyFrame();
        e.printStackTrace(); // print to console

        JLabel errorHeader = new JLabel("<html>&nbsp;Caught an error:&nbsp;</html>");
        JLabel errorText = new JLabel("<html>&nbsp;"+ e.toString() +"&nbsp;</html>");
        JButton restartButton = new JButton("Restart Program");
        JButton exitProgramButton = new JButton("Exit Program");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        panel.add(errorHeader);
        panel.add(errorText);
        panel.add(restartButton);
        panel.add(exitProgramButton);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

        // button listeners

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // add text box repo to database
                try {
                    init();
                } catch (Exception e) {

                    handleError(e);
                }
            }
        });

        exitProgramButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // add text box repo to database
                frame.dispose();
                System.exit(0);
            }
        });
    }

}
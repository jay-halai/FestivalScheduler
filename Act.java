import java.time.*;

/**
  * Act class used to create an object of act which stores the variables for each act.
  * Each act should have an ID,name,duration,priority and start time
**/
public class Act {

    private int actID;
    private String actName;
    private int actDuration;
    private int actPriority;
    private LocalTime startTime; 

    /**
     * Constructor which creates an instance of act and runs each time the instance is called
     *
     * @param actID passed to act so can be stored in act class
     * @param actName passed to act so can be stored in act class
     * @param actDuration passed to act so can be stored in act class
     * @param actPriority passed to act so can be stored in act class
     * @param startTime passed to act so can be stored in act class
     **/
    public Act(int actID,String actName, int actDuration,int actPriority, LocalTime startTime){
        this.actID = actID;
        this.actName = actName;
        this.actDuration = actDuration;
        this.actPriority = actPriority;
        this.startTime = startTime; 
    }


    /**
      *The getActID class allows you to get the get the variable actID.
      *@return actID The ID the user enters
     **/
    public int getActID(){
        return actID;
    }

    /**
      *The getActName function allows you to get the get the variable actName.
      *@return actName The Name the user enters
     **/
    public String getActName() {
        return actName;
    }

    /**
      *The getActLength function allows you to get the get the variable actLength.
      *@return actDuration The Duration the user enters
     **/
    public int getActLength() {
        return actDuration;
    }

    /**
      *The getActPriority function allows you to get the get the variable actPriority.
      *@return actPriority The Priority the user enters
     **/
    public int getActPriority() {
        return actPriority;
    }

    /**
      *The getStartTime function allows you to get the get the variable actStartTime.
      *@return startTime The Start Time the user enters
     **/
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
      *The setActID function allows you to set the value of actID.
      *@param actID needs to be passed to set acts actID
      *@return changes Act actID to users actID
     **/
    public int setActID(int actID){
        return this.actID = actID;
    }

    /**
      *The setActName function allows you to set the value of actName.
      *@param actName needs to be passed to set acts actName
      *@return changes Act actName to users actName
     **/
    public String setActName(String actName) {
        return this.actName = actName;
    }

    /**
      *The setActDuration function allows you to set the value of actDuration.
      *@param actLength needs to be passed to set acts actLength
      *@return changes Act actDuration to users actLength
     **/
    public int setActLength(int actLength) {
        return this.actDuration = actLength;
    }

    /**
      *The setActPriority function allows you to set the value of actPriority.
      *@param actPriority needs to be passed to set acts actPriority
      *@return changes Act actPriority to users actPriority
     **/
    public int setActPriority(int actPriority) {
        return this.actPriority = actPriority;
    }

    /**
      *The setStartTime function allows you to set the value of startTime.
      *@param startTime needs to be passed to set acts startTime
      *@return changes Act startTime to users startTime
     **/
    public LocalTime setStartTime(LocalTime startTime) {
        return this.startTime = startTime;
    }


    /**
      *The toString function adds the concatanates all the vairables to a string method
      *@return returns the string of act attributes
     **/
    @Override
    public String toString() {
        return this.actID + "," + this.actName + "," + this.actDuration + "," + this.actPriority + "," + this.startTime;
    }

    /**
      *The compareTo function checks if the current actPriority is equal to,less than or larger than act Priority
      *Stored in the arraylist actList
      *@param act the string of act attributes
      *@return res which is the result of the check
     **/
    public int compareTo(Act act) {
        int res = 0;
        if (this.actPriority < act.getActPriority()) {
            res =- 1;
        }
        if (this.actPriority > act.getActPriority()) {
            res = 1;
        }
        return res;
    }




    
}

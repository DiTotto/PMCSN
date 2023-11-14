package main.java.datastruct;

public class NodeState {

    private int jobIN; /* numero di job nel centro */
    private int jobOUT; /* numero di job che escono dal centro per completamento*/
    private int jobLEFT; /* numero di job che abbandonano il sistema*/
    private int feedbackIN; /* numero di job che entrano in feedback*/
    private int otherIN; /* numero di job che entrano da altro centro*/
    public NodeState() {
        this.jobIN = 0;
        this.jobOUT = 0;
        this.feedbackIN = 0;
        this.otherIN = 0;
        this.jobLEFT = 0;
    }

    public NodeState(int jobIN, int jobOUT, int feedbackIN, int otherIN, int jobLEFT) {
        this.jobIN = jobIN;
        this.jobOUT = jobOUT;
        this.feedbackIN = feedbackIN;
        this.otherIN = otherIN;
        this.jobLEFT = jobLEFT;
    }

    public int getJobIN() {
        return jobIN;
    }

    public void setJobIN(int jobIN) {
        this.jobIN = jobIN;
    }

    public int getJobOUT() {
        return jobOUT;
    }

    public void setJobOUT(int jobOUT) {
        this.jobOUT = jobOUT;
    }

    public int getFeedbackIN() {
        return feedbackIN;
    }

    public void setFeedbackIN(int feedbackIN) {
        this.feedbackIN = feedbackIN;
    }

    public int getOtherIN() {
        return otherIN;
    }

    public void setOtherIN(int otherIN) {
        this.otherIN = otherIN;
    }

    public int getJobLEFT() {
        return jobLEFT;
    }

    public void setJobLEFT(int jobLEFT) {
        this.jobLEFT = jobLEFT;
    }



}

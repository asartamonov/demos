package apitypes;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://somwereintheweb.com")
public class SumRequest {

    private int first;
    private int second;

    @XmlElement(namespace = "http://otherweb.com")
    public int getFirst() {
        return first;
    }

    @XmlElement(namespace = "http://otherweb.com")
    public int getSecond() {
        return second;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "SumRequest{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}

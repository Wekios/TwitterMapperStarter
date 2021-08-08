package filters;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

public class OrFilter implements Filter {
    private final Filter firstChild;
    private final Filter secondChild;

    public OrFilter(Filter firstChild, Filter secondChild) {
        this.firstChild = firstChild;
        this.secondChild = secondChild;

    }

    /**
     * A and filter matches when either child matches
     * @param s     the tweet to check
     * @return      whether or not it matches
     */
    @Override
    public boolean matches(Status s) {
        return firstChild.matches(s) || secondChild.matches(s);
    }

    @Override
    public List<String> terms() {
        List<String> ans = new ArrayList<>(2);
        ans.add(firstChild.terms().get(0));
        ans.add(secondChild.terms().get(0));
        return ans;
    }

    public String toString() {
        return "(" + firstChild.toString() + " or " + secondChild.toString() + ")";
    }
}

package distributed.joiner;

import java.io.Serializable;

public interface Joiner extends Serializable {
    void join();
    void finished();
}

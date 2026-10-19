package distributed.barrier;

import java.io.Serializable;

public interface Barrier extends Serializable {
    void barrier();
}

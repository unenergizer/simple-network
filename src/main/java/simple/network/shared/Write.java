package simple.network.shared;

import java.io.ObjectOutputStream;

@FunctionalInterface
public interface Write {
    void accept(ObjectOutputStream outStream);
}

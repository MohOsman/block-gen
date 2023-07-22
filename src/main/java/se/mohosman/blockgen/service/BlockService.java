package se.mohosman.blockgen.service;

import se.mohosman.blockgen.model.Block;

public interface BlockService {

    byte[] createBlock(String data);
    byte[] createGenesisBLock(String data);
}

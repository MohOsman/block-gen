package se.mohosman.blockgen.service.impl;

import org.springframework.stereotype.Service;
import se.mohosman.blockgen.model.Block;
import se.mohosman.blockgen.service.BlockService;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class BlockServiceImplementation implements BlockService {

    @Override
    public byte[] createBlock(String Data) {
        return new byte[0];
    }

    @Override
    public byte[] createGenesisBLock(String data){
        Block Block = new Block(0,new byte[]{0},
                Timestamp.valueOf(String.valueOf(Instant.now()))
                ,data.getBytes());

        // hash the block
        //  save it to the hash
        // put it the blockchain
        return new byte[0];

    }
}

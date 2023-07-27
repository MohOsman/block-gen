package se.mohosman.blockgen.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
public class Block {

    private int index;
    private byte[] hash;
    private byte [] previousHash;
    private Timestamp timestamp;
    private byte[] data;

    public Block(int index, byte[] previousHash, Timestamp timestamp, byte[] data) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.data = data;
    }





}

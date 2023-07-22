package se.mohosman.blockgen.model;

import java.util.LinkedList;

public record Blockchain(LinkedList<Block> blocks) {
}

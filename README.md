


[![Java CI with Gradle](https://github.com/MohOsman/block-gen/actions/workflows/gradle.yml/badge.svg)](https://github.com/MohOsman/block-gen/actions/workflows/gradle.yml)

# Simple Blockchain Generator App
[![Java CI with Gradle](https://github.com/MohOsman/block-gen/actions/workflows/gradle.yml/badge.svg)](https://github.com/MohOsman/block-gen/actions/workflows/gradle.yml)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Description

The Blockchain Generator App is a Spring Boot application that serves as a blockchain generator and manager. It provides a RESTful API for creating new blocks and inserting them into the blockchain. The blockchain is a Linklist, immutable ledger that securely stores a sequence of blocks, each containing a cryptographic hash of the previous block.
For simplicity for now the data input is string 


The application will run on the default port 8080.

2. Create a new block:

- **Endpoint:** `POST /createBlock`
- **Request Body:** Provide the data for the new block as a string.
- **Response:** The response will contain the cryptographic hash of the created block as a plain text response.

3. Retrieve an existing block:

- **Endpoint:** `GET /block/{hash}`
- **URL Parameter:** Replace `{hash}` with the hexadecimal representation of the cryptographic hash for the desired block.
- **Response:** The response will contain the block details in JSON format.

4. Blockchain Generation:

- The application automatically generates a blockchain when a new block is created.
- Each block is cryptographically linked to the previous block using its hash, ensuring the integrity of the entire blockchain.
- Once a block is created, it is appended to the blockchain, forming a chain of blocks in chronological order.

## Configuration

The application uses a `BlockService` to handle blockchain-related operations. The `BlockService` is injected into the router class via constructor-based dependency injection. The `BlockService` encapsulates the logic for creating blocks, computing cryptographic hashes, and managing the blockchain.

## HexUtils

The application includes a utility class named `HexUtils`, which provides methods to convert byte arrays to hexadecimal strings and vice versa. This utility is used to convert cryptographic hash values between their byte representation and human-readable hexadecimal format.

## Dependencies

The Blockchain Generator App relies on the following libraries and frameworks:

- Spring Boot: Provides the core functionality for creating a web application.
- Spring WebFlux: Enables reactive programming and non-blocking I/O for handling HTTP requests.
- Reactor: Provides the reactive types `Mono` and `Flux` for asynchronous processing.
- Apache Maven: Manages project dependencies and builds the application.

## Contributions

Contributions to the project are welcome! If you find any issues or have suggestions for improvements, please create a new issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

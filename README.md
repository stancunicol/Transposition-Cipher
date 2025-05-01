# Transposition Cipher - Client-Server Application

## Overview
A Java implementation of a transposition cipher using a client-server architecture. The application allows secure message encryption on the client side and decryption on the server side using a columnar transposition cipher with a user-provided key.

## Features
- **Secure Encryption**: Implements columnar transposition cipher algorithm
- **Client-Server Architecture**: Network-based communication between components
- **Key Validation**: Ensures keys contain only distinct letters
- **Case Insensitive Keys**: Treats key letters case-insensitively for validation
- **Padding Handling**: Automatically removes padding during decryption

## How It Works
1. **Client Side**:
   - Accepts plaintext message and encryption key
   - Validates key (must contain only distinct letters)
   - Creates a matrix using the key length
   - Fills matrix with message (adds padding if needed)
   - Encrypts by reading columns in alphabetical key order

2. **Server Side**:
   - Receives encrypted message and key
   - Reconstructs the encryption matrix
   - Decrypts by reading rows in original order
   - Removes any padding characters
   - Returns decrypted message to client

## Technical Details
- **Encryption Algorithm**: Columnar transposition cipher
- **Network Protocol**: TCP sockets
- **Port**: 12345
- **Key Requirements**:
  - Must contain only letters (a-z, A-Z)
  - All letters must be distinct (case insensitive)
  - Minimum length: 1 character

# Example

![image](https://github.com/user-attachments/assets/113bb1ad-128f-45bf-b3fa-d11f9d3ae9e3)

![image](https://github.com/user-attachments/assets/bf361acd-ad69-4c07-93a8-74e5368d09f8)

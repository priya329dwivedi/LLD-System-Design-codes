# Document Parser Factory - Factory Pattern

## Problem Statement

Design a system that can parse different types of documents such as PDF, Word, and CSV files.

## Parser Types

The system should support the following document parsers:

1. **PDF Parser** - Parses PDF documents and extracts content.
2. **Word Parser** - Parses Word (.docx) documents and extracts content.
3. **CSV Parser** - Parses CSV files and extracts content.

## Requirements

- Each document type requires a different parser implementation.
- The client should simply request a parser based on the file type **without worrying about the object creation logic**.
- The system should **centralize the creation** of parser objects.
- It should be easy to add support for new document types in the future (e.g., JSON or XML) **without modifying the client code**.

## Why Factory Pattern?

This is a good use case for the **Factory Pattern**, where a factory class creates the appropriate parser object based on the document type. This allows:

- Centralizing object creation logic in one place.
- The client to remain decoupled from concrete parser implementations.
- Adding new document parsers without changing existing client code (Open/Closed Principle).

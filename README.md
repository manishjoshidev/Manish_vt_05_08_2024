# URL Shortener Service

## Overview
A URL shortener service that provides the following features:
- Generates a short and unique alias for a given long URL.
- Redirects short links to the original URL.
- Links expire after a default time span of 10 months.
- Ensures high availability and minimal latency for URL redirection.
- Generates non-predictable short URLs with a length of up to 30 characters, starting with the prefix `http://localhost:8080/`.

## Assumptions
- The service is expected to handle 5 million new URL shortenings per month.
- The read/write ratio is expected to be 100:1.

## Database
- PostgreSQL is used as the database for storing URL mappings and metadata.

## API Endpoints

### 1. Shorten URL

**Endpoint**: `POST /shorten`  

**Description**: Generates a short URL for the given destination URL.  

**Request Body**:
```json
{
  "destinationUrl": "https://example.com"
}


{
  "shortUrl": "http://localhost:8080/abc123",
  "id": "12345"
}

{
  "success": true
}


{
  "shortUrl": "http://localhost:8080/abc123"
}

{
  "destinationUrl": "https://example.com"
}


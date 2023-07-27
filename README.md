# Text File Analysis

The File Analysis is a Spring Boot application that allows you to analyse text files. It provides endpoints for uploading, get random line, get the largest line of input files.

# REST APIs

## Manage upload and get File

The REST APIs to manage files is described below.
- Supported Accept Header:
  - application/*
- Supported Content Type :
  - multipart/form-data 

### Get list of files

#### Request

`GET /v1/file/`

    curl -i -H 'Accept: application/json' http://localhost:8080/api/v1/file/

#### Response

    HTTP/1.1 200 OK
    Date: Fri, 14 Jul 2023 22:49:15 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json

    [
        "test.txt",
        "test1.txt"
    ]

### Upload file

#### Request

`POST /v1/file/`

    curl --location 'http://localhost:8080/api/v1/file' --form 'file=@"/Users/meysamzamani/test.txt"'

#### Response

    HTTP/1.1 201 CREATED
    Date: Fri, 14 Jul 2023 22:49:15 GMT
    Status: 201 CREATED
    Connection: close
    Content-Type: text/plain;charset=UTF-8
    Content-Length: 50

    You successfully uploaded sample.txt

## File analysis end points

The REST APIs to analyse files is described below with one example.
- Supported Accept Header:
    - application/json
    - application/xml
    - text/plain
- Supported Content Type :
    - application/* **(If Content-Type header is sent in the request, more complete information will be returned)**

### Get one random line from last file

#### Request

`GET /v1/random?mode=LAST`

    curl --location 'http://localhost:8080/api/v1/random?mode=LAST' --header 'Accept: application/json' --header 'Content-Type: */*'

#### Response

    HTTP/1.1 200 OK
    Date: Sat, 15 Jul 2023 19:07:46 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 97

    {
    "text": "Test666",
    "fileName": "test.txt",
    "textLength": 7,
    "lineNumber": 0,
    "oftenOccurredLetter": "6"
    }

### Get one random line from one random file

#### Request

`GET /v1/random?mode=RANDOM`

    curl --location 'http://localhost:8080/api/v1/random?mode=ALL' --header 'Accept: application/xml --header 'Content-Type: */*'

#### Response

    HTTP/1.1 200 OK
    Date: Sat, 15 Jul 2023 19:11:31 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/xml
    Content-Length: 172

    <FileTextLine>
        <text></text>
        <fileName>alice29.txt</fileName>
        <textLength>0</textLength>
        <lineNumber>3496</lineNumber>
        <oftenOccurredLetter></oftenOccurredLetter>
    </FileTextLine>

### Get N number of the longest unique line from one random file

#### Request

`GET /v1/longest/20/unique?mode=RANDOM`

        curl --location 'http://localhost:8080/api/v1/longest/20/unique?mode=RANDOM' --header 'Accept: application/json'

#### Response

    HTTP/1.1 200 OK
    Date: Sat, 15 Jul 2023 20:12:19 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/jspn
    Content-Length: 247

    [
        {
            "text": "and she crossed her hands on her lap as if she were saying lessons,"
        },
        {
            "text": "down looking for it, while the rest of the party went back to the game."
        },
        {
            "text": "said Alice)--`and perhaps you were never even introduced to a lobster--'"
        }
        ...
    ]

### Get N number of the longest unique line from all files

#### Request

`GET /v1/longest/100/unique?mode=ALL`

        curl --location 'http://localhost:8080/api/v1/longest/100/unique?mode=ALL' --header 'Accept: application/json'

#### Response

    HTTP/1.1 200 OK
    Date: Sat, 15 Jul 2023 20:12:19 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 247

    [
        {
            "text": "and she crossed her hands on her lap as if she were saying lessons,"
        },
        {
            "text": "down looking for it, while the rest of the party went back to the game."
        },
        {
            "text": "said Alice)--`and perhaps you were never even introduced to a lobster--'"
        }
        ...
    ]

### Get N number of the longest line from one random file

#### Request

`GET /v1/longest/20?mode=RANDOM`

        curl --location 'http://localhost:8080/api/v1/longest/20?mode=RANDOM' --header 'Accept: application/json --header 'Content-Type: */*'

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Jul 2023 19:56:40 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/jspn
    Content-Length: 513

    [
      {
          "text": "ROSALIND\tSo I do: but, i' faith, I should have been a woman by right.",
          "fileName": "asyoulik.txt",
          "textLength": 69,
          "lineNumber": 3393,
          "oftenOccurredLetter": " "
      },
      {
          "text": "TOUCHSTONE\tAccording to the fool's bolt, sir, and such dulcet diseases.",
          "fileName": "asyoulik.txt",
          "textLength": 71,
          "lineNumber": 3902,
          "oftenOccurredLetter": " "
      },
      {
          "text": "SIR OLIVER MARTEXT\tTruly, she must be given, or the marriage is not lawful.",
          "fileName": "asyoulik.txt",
          "textLength": 75,
          "lineNumber": 2457,
          "oftenOccurredLetter": " "
      }
    ]
    ...

### Get N number of the longest line from all files

#### Request

`GET /v1/longest/100?mode=ALL`

        curl --location 'http://localhost:8080/api/v1/longest/100?mode=ALL' --header 'Accept: application/json --header 'Content-Type: */*'

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Jul 2023 19:56:40 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/jspn
    Content-Length: 513

    [
      {
          "text": "ROSALIND\tSo I do: but, i' faith, I should have been a woman by right.",
          "fileName": "asyoulik.txt",
          "textLength": 69,
          "lineNumber": 3393,
          "oftenOccurredLetter": " "
      },
      {
          "text": "TOUCHSTONE\tAccording to the fool's bolt, sir, and such dulcet diseases.",
          "fileName": "asyoulik.txt",
          "textLength": 71,
          "lineNumber": 3902,
          "oftenOccurredLetter": " "
      },
      {
          "text": "SIR OLIVER MARTEXT\tTruly, she must be given, or the marriage is not lawful.",
          "fileName": "asyoulik.txt",
          "textLength": 75,
          "lineNumber": 2457,
          "oftenOccurredLetter": " "
      }
    ]
    ...
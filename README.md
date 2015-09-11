## /api/getScores
#### Auth with login and password
`/api/getScores?login=*****&password=*****`

#### Auth with hashed session phpsessid
`/api/getScores?login=*****&hash=******`

Returns JSON with student info and scores.

### API v2
```json
{
    "student": {
        "name": "Сімашко Юлія Володимирівна ",
        "group": "МЕЄЕ-11",
        "firstSemester": {
            "subjects": [{
                "name": "Вища математика",
                "controlType": "Залік",
                "modules": [{
                    "date": "24.10.14",
                    "weight": 30,
                    "score": 90
                }, {
                    "date": "22.11.14",
                    "weight": 40,
                    "score": 98
                }, {
                    "date": "26.11.14",
                    "weight": 30,
                    "score": 90
                }]
            }]
        },
        "secondSemester": {
            "subjects": []
        }
    },
    "phpsessid": "rO0ABXNyABdqYXZhLnV0aWwuTGlua2VkSGFza...",
    "success": true
}
```

### API v1
```json
{
    "name": "Сімашко Юлія Володимирівна ",
    "group": "МЕЄЕ-11",
    "firstSemester": {
        "subjects": [{
            "name": "Вища математика",
            "controlType": "Залік",
            "modules": [{
                "date": "24.10.14",
                "weight": 30,
                "score": 90
            }, {
                "date": "22.11.14",
                "weight": 40,
                "score": 98
            }, {
                "date": "26.11.14",
                "weight": 30,
                "score": 90
            }]
        }]
    },
    "secondSemester": {
       "subjects": []
    }
}
```

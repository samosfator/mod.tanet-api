## /api/getScores
#### Auth with login and password

`/api/getScores?login=*****&password=*****`

#### Auth with session hash

1. `/api/getHash?login=*****&password=*****`
2. `/api/getScores?login=*****&hash=******`

Returns JSON with student info and scores.

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

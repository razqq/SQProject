const express = require('express')
const app = express()
const cookieSession = require('cookie-session')
const {data} = require("express-session/session/cookie");

app.use('/css', express.static(__dirname + '/css'))
app.engine('html', require('ejs').renderFile);
app.use(express.json())
app.use(express.urlencoded({extended: false}))
app.use(cookieSession({
    name: 'cc-session',
    keys: ['key1', 'key2']
  }))

app.get('/', function (req, res) {
    res.render('index.ejs')
})

app.get('/timetable', async function (req, res) {
    fetch("http://localhost:8080/api/schedule/get")
        .then(function (response) {
            return response.json();
        }).then(function (data){
            console.log(data)
            res.render('timetable.ejs', data)
    })
})

app.post('/result', function (req, res) {
  
  const data = {  
    teacher: req.body.teacher,
    room: req.body.room,
    group: req.body.group,
    subject: req.body.subject,
    day: req.body.day,
    begin_time: req.body.begin_time,
    end_time: req.body.end_time
  }

  console.log(data)

  res.render('result.ejs',data)
})

app.listen(3000, () => console.log(`App running in http://localhost:3000`))
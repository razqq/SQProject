const express = require('express')
const app = express()
const cookieSession = require('cookie-session')

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

app.get('/timetable', function (req, res) {
    res.render('timetable.ejs')
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
  res.render('result.ejs',data)
})

app.listen(8080, () => console.log(`App listening on port ${8080}!`))
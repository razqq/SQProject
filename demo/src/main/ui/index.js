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

function prepareTimeslotData(timeslot) {
    let timeslotValue = `${timeslot.subject.name} - ${timeslot.classType} - 
    ${timeslot.teacher.name}`

    if (timeslot.classType === "Course"){
        timeslotValue += ` - Year ${timeslot.subject.year}`
    } else{
        timeslotValue += ` - Group ${timeslot.studGroup.year}${timeslot.studGroup.groupName}`
    }

    timeslotValue += ` - ${timeslot.room.name}`;
    return timeslotValue;
}

function prepareEmptyJson(){
    const days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];
    const times = ["8", "10", "12", "14", "16", "18"];
    let json = {}
    days.forEach(
        (day) => {
            json[day] = {}
            times.forEach(
                (time) => {
                    json[day][time] = '';
                }
            )
        }
    )
    return json;
}
function parseJson(data){
    let new_json = prepareEmptyJson();
    data.forEach(function(obj){
        let day = obj.day
        let startTime = obj.startTime
        if (!new_json[day]){
            new_json[day] = {}
        }
        new_json[day][startTime] = prepareTimeslotData(obj);
    })
    return new_json;
}

app.get('/timetable', async function (req, res) {
    fetch("http://localhost:8080/api/schedule/get")
        .then(function (response) {
            return response.json();
        }).then(function (data) {
            console.log(data);
        let new_data = parseJson(data);
        console.log(new_data)
        res.render('timetable.ejs', new_data)
    })
})

app.post('/result', async function (req, res) {

    const data = {
        teacher: req.body.teacher,
        room: req.body.room,
        group: req.body.group,
        subject: req.body.subject,
        day: req.body.day,
        begin_time: req.body.begin_time,
        end_time: req.body.end_time,
        class_type: req.body.classType
    }

    fetch('http://localhost:8080/api/schedule/add?' + new URLSearchParams({
        startTime: data.begin_time.split(":")[0],
        endTime: data.end_time.split(":")[0],
        day: data.day,
        StudGroup: data.group,
        teacher: data.teacher,
        subject: data.subject,
        classType: data.class_type,
        room: data.room

    }),
        {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
        return response.json();
    }).then(function (responseData) {
        console.log(responseData)
        if ('errorMessage' in responseData){
            data.error_message = responseData.errorMessage
        }
        console.log(data)
        res.render('result.ejs', data)
    })

})

app.listen(3000, () => console.log(`App running in http://localhost:3000`))

module.exports = app;
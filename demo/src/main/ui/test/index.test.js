const assert = require('assert');
const expect = require('chai').expect
const request = require('supertest');
const request_form = require('supertest')('http://xxx:3000');
const app = require('../index');
const superagent = require('superagent');

describe('Unit testing the / route', function() {

    it('should return OK status', function() {
      return request(app)
        .get('/')
        .then(function(response){
            assert.equal(response.status, 200)
        })
    });
});

describe('Unit testing the /result route', function() {

    it('should return OK status', function(done) {
        request_form.post('/xxx')
                    .type('form')
                    .send({teacher:"Iftene Adrian",subject:"Programare avansata",room:"C308",group:"A1",day:"Monday",begin_time:"8:00",end_time:"10:00"})
                    .end(function(err, res) {
                        if (err) {
                            console.log("------  error  ------");
                            throw err;
                        }
                        assert.ok(res);
                        assert.ok(res.body);
                        assert.equal(res.status, 200);
                        res.body.should.have.property('trial');
                        done();
            });
    });
});

describe('Unit testing the /timetable route', function() {

    it('should return OK status', function(done) {
      request(app)
        .get('/timetable')
        .then(function(response){
            assert.equal(response.status, 200)
        })
        done();
    });
});

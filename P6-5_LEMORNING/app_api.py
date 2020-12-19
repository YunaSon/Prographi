from flask import Flask, request, jsonify
from flask_restful import Resource, Api, reqparse
import werkzeug
from deep.model import DeepModel

app = Flask(__name__)
api = Api(app)

UPLOAD_FOLDER = './uploads/audio'
ALLOWED_EXTENSIONS = set(['m4a', 'm4p', 'wav', 'mp3', 'mp4'])
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

TAGS = {
    "filename":"filename",
    "tags": [],
}
parser = reqparse.RequestParser()
parser.add_argument('tags')

class HelloWorld(Resource):
    def get(self):
        return {"hello": {"world":None}}

api.add_resource(HelloWorld,'/')


class Tag(Resource):
    def get(self):
        return TAGS
    
    def post(self):
        return TAGS

api.add_resource(Tag, '/tag')

if __name__ == '__main__':
    app.run(debug=True)

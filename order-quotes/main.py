import random
import requests
import logging
from flask import Flask, request, make_response

from utils import process

app = Flask("Py-Flask-App")

@app.route("/quote", methods=["GET"])
def quote():
    process(random.randint(0, 25))
    return make_response({}, 200)


@app.route("/calc", methods=["GET"])
def calc():
    process(random.randint(0, 25))
    return make_response({}, 200)

def main():
    app.run(host='0.0.0.0', port=8090, debug=False)
    
if __name__ == "__main__":
    main()

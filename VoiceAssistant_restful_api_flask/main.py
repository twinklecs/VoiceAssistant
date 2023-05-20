import pyodbc

from flask import Flask, jsonify

import openai

openai.api_key = "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"

app = Flask(__name__)

servername = "DESKTOP-AOHKIAH\SQLEXPRESS"
databasename = "User_VoiceAssistant"

connectionString = ('Driver={SQL Server};Server='+servername+';Trusted_Connection=yes;Database='+databasename+';')

connection = pyodbc.connect(connectionString, autocommit=True)
dbCursor = connection.cursor()

@app.route('/<string:answer>', methods=['GET'])
def get_answer(answer):
    completion = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=[
            {"role": "user", "content": answer}
        ]
    )
    #print(completion.choices[0].message.content)
    return jsonify(message=completion.choices[0].message.content)

@app.route('/db', methods=['GET'])
def get_db():
    dbCursor.execute('SELECT * FROM Account')
    row_headers = [x[0] for x in dbCursor.description]  # this will extract row headers
    rv = dbCursor.fetchall()
    json_data = []
    for result in rv:
        json_data.append(dict(zip(row_headers, result)))
    return jsonify(json_data)

if __name__ == '__main__':
    app.run(host='0.0.0.0')

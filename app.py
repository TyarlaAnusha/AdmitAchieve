# 1. Install uvicorn and fastapi
# pip install fastapi uvicorn

# 1. Imports Libraries
import uvicorn
from fastapi import FastAPI

from fastapi.middleware.cors import CORSMiddleware
import pickle

# 2. Create the  app object
app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
#3.load the model
rgModel = pickle.load(open("1024.pkl", "rb"))

# 4. Index route, opens automatically on http://127.0.0.1:80
@app.get('/')
def index():
    return {'message': 'Hello, World'}

# 5. Run the API with uvicorn
if __name__ == '__main__':
    uvicorn.run(app, port=80, host='0.0.0.0')

#----------------------

@app.get("/predictProbability")
def gePredictProbability(GRE: int, TOEFL: int,Rating: int,CGPA: float):
    #rgModel = pickle.load(open("1024.pkl", "rb"))
    
    prediction = rgModel.predict([[GRE,TOEFL,Rating,CGPA]])
    return {'prediction': prediction[0]}


# uvicorn app:app --host 0.0.0.0 --port 80
# http://127.0.0.1/predictProbability?GRE=324&TOEFL=107&Rating=1&CGPA=8.87
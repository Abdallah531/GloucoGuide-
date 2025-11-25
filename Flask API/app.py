import logging
from flask import Flask, request, jsonify
import joblib
from sklearn.preprocessing import StandardScaler, LabelEncoder
import pandas as pd

logging.basicConfig(level=logging.INFO)
app = Flask(__name__)

try:
    rf_model = joblib.load('rf_model.pkl')
    app.logger.info("Model loaded successfully.")
except Exception as e:
    app.logger.error(f"Error loading model: {str(e)}")
    
try:
    label_encoder = joblib.load('label_encoder.pkl')
    app.logger.info("Label encoder loaded successfully.")
except Exception as e:
    app.logger.error(f"Error loading label encoder: {str(e)}")

try:
    scaler = joblib.load('scaler.pkl')
    app.logger.info("Scaler loaded successfully.")
except Exception as e:
    app.logger.error(f"Error loading scaler: {str(e)}")

prediction_mapping = {1: "Positive", 0: "Negative"}

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()
        app.logger.info(f"Received data: {data}")

        df = pd.DataFrame([data])
        df = df.astype(int)
        
        app.logger.info(f"DataFrame: {df}")

        prediction = rf_model.predict(df)
        app.logger.info(f"Prediction: {prediction}")

        mapped_predictions = [prediction_mapping[p] for p in prediction]
        return jsonify({'prediction': mapped_predictions})

    except Exception as e:
        app.logger.error(f"Error: {str(e)}")
        return jsonify({'error': str(e)})

if __name__ == '__main__':
    app.run(debug=True)

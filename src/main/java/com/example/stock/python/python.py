from kafka import KafkaProducer
import json

# Kafka 프로듀서 설정
producer = KafkaProducer(
    bootstrap_servers='여기에_당신의_Kafka_서버를_넣어주세요',
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

# 주식 데이터 예시
stock_data = [
    {"id": 1, "first_name": "$11.93"},
    {"id": 2, "first_name": "$25.48"},
    # ... 다른 주식 데이터
]

# 토픽 1에 데이터 보내기
producer.send('realtime_stock_data', value=stock_data)

# 최고 상승률 종목 데이터 예시
top_stock_increase_data = [
    {"id": 3, "first_name": "$0.84"},
    {"id": 11, "first_name": "$26.00"},
    # ... 다른 최고 상승률 종목 데이터
]

# 토픽 2에 데이터 보내기
producer.send('top_stock_increase', value=top_stock_increase_data)

# 프로듀서 종료
producer.close()

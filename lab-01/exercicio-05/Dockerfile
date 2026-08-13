FROM python:3.14-slim

WORKDIR /app

# flask: framework para aplicações web simples
# redis: banco de dados de chave-valor, cache e agente de mensagens
RUN pip install flask redis

# cria variáveis de ambiente
ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0
ENV PYTHONUNBUFFER=1

# porta
EXPOSE 5000

CMD [ "flask", "run" ]

#!/usr/bin/env sh
ls -lAR /home/pythonuser
python /home/pythonuser/booking_reference_service/booking_reference_service.py &
python /home/pythonuser/train_data_service/start_service.py /home/pythonuser/train_data_service/trains.json

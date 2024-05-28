import matplotlib.pyplot as plt

#path = 'output/cultura/transient/'
path = '../../tmp/'


with open(path + 'outputTempoMedioRisposta2.txt', 'r') as file:
    stringhe_numeriche = file.readlines()
with open(path + 'outputTempoMedioRisposta3.txt', 'r') as file:
    stringhe_numeriche1 = file.readlines()
with open(path + 'outputTempoMedioRisposta4.txt', 'r') as file:
    stringhe_numeriche2 = file.readlines()
# with open(path + 'outputTempoMedioRisposta8.txt', 'r') as file:
#     stringhe_numeriche3 = file.readlines()

valori_numerici = [float(valore.strip()) for valore in stringhe_numeriche]
valori_numerici1 = [float(valore.strip()) for valore in stringhe_numeriche1]
valori_numerici2 = [float(valore.strip()) for valore in stringhe_numeriche2]
# valori_numerici3 = [float(valore.strip()) for valore in stringhe_numeriche3]


plt.plot(valori_numerici, label='N = 2', color='black')
plt.plot(valori_numerici1, label='N = 3', color='green')
plt.plot(valori_numerici2, label='N = 4', color='blue')
# plt.plot(valori_numerici3, label='N = 8', color='orange')

plt.grid(True)
plt.xlim(0, 256)

#yM = 389.90
yQ = 300

#yS = yQ * 2.5

plt.ylim(0, 1000)

plt.axhline(y=yQ, color='red', linestyle='--', label='QoS')

#plt.axhline(y=yM, color='blue', linestyle='--', label='Valor medio atteso')

# Aggiungi etichette personalizzate lungo l'asse y per una linea parallela all'asse x
posizione_x = 0  # Sostituisci con l'indice desiderato lungo l'asse x
valore_y = yQ   # Sostituisci con il valore desiderato lungo l'asse y
testo_etichetta = '{}'.format(valore_y)

#posizione_x1 = 0  # Sostituisci con l'indice desiderato lungo l'asse x
#valore_y1 = yM   # Sostituisci con il valore desiderato lungo l'asse y
#testo_etichetta1 = '{}'.format(valore_y1)

plt.text(posizione_x, valore_y, testo_etichetta, color='black', fontsize=8, ha='right', va='center')
#plt.text(posizione_x1, valore_y1, testo_etichetta1, color='black', fontsize=8, ha='right', va='top')


# Aggiungi etichette agli assi e un titolo
plt.xlabel('# Simulazione')
plt.ylabel('Valore E[Ts]')
plt.title('E[Ts] centro Cultura')
plt.legend()

# Mostra il grafico
plt.show()

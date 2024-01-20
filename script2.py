import matplotlib.pyplot as plt

path = 'output/cultura/transient/'

# Leggi il file e ottieni una lista di stringhe
with open(path + 'outputTempoMedioRisposta.txt', 'r') as file:
    stringhe_numeriche = file.readlines()

# Converti le stringhe in valori numerici
valori_numerici = [float(valore.strip()) for valore in stringhe_numeriche]

# Crea un grafico
plt.plot(valori_numerici, label='E[Ts]', color='black')

plt.grid(True)
plt.xlim(0, 256)

yM = 389.90
yQ = 300

yS = yM * 2.5

plt.ylim(0, yS)

plt.axhline(y=yQ, color='red', linestyle='--', label='QoS')

plt.axhline(y=yM, color='blue', linestyle='--', label='Valor medio atteso')

# Aggiungi etichette personalizzate lungo l'asse y per una linea parallela all'asse x
posizione_x = 0  # Sostituisci con l'indice desiderato lungo l'asse x
valore_y = yQ   # Sostituisci con il valore desiderato lungo l'asse y
testo_etichetta = '{}'.format(valore_y)

posizione_x1 = 0  # Sostituisci con l'indice desiderato lungo l'asse x
valore_y1 = yM   # Sostituisci con il valore desiderato lungo l'asse y
testo_etichetta1 = '{}'.format(valore_y1)

plt.text(posizione_x, valore_y, testo_etichetta, color='black', fontsize=8, ha='right', va='center')
plt.text(posizione_x1, valore_y1, testo_etichetta1, color='black', fontsize=8, ha='right', va='top')


# Aggiungi etichette agli assi e un titolo
plt.xlabel('# Simulazione')
plt.ylabel('Valore E[Ts]')
plt.title('E[Ts] centro Cultura')
plt.legend()

# Mostra il grafico
plt.show()

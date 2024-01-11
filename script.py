import pandas as pd
import matplotlib.pyplot as plt

# Leggi i dati dal file CSV
df = pd.read_csv('job_data.csv')
# Crea un grafico a dispersione
plt.scatter(df['Time'], df['TotalJobs'])

# Aggiungi etichette agli assi e un titolo al grafico
plt.xlabel('Time')
plt.ylabel('Total Jobs')
plt.title('Scatter Plot of Time vs Total Jobs')

# Mostra il grafico
plt.show()

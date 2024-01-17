import pandas as pd
import matplotlib.pyplot as plt


df = pd.read_csv('outputCSV/serviziSociali/jobServiti.csv')
df = df.sort_values(by='Time')


plt.plot(df['Time'], df['TotalJobs'], linestyle='-', color='black', label='Total Jobs')

plt.xlabel('Time')
plt.ylabel('Job nel nodo')
plt.title('Job totali nel centro servizi Sociali con E(S) = 12.5 minuti')
plt.legend()
plt.show()


df = pd.read_csv('outputCSV/serviziSociali/rho.csv')
df = df.sort_values(by='Time')


plt.plot(df['Time'], df['Rho'], linestyle='-', color='black', label='Utilizzazione')

plt.xlabel('Time')
plt.ylabel('Utilizzazione nodo')
plt.title('Utilizzazione nel centro servizi Sociali con E(S) = 12.5 minuti')
plt.legend()
plt.show()

df = pd.read_csv('outputCSV/serviziSociali/attesa.csv')
df = df.sort_values(by='Time')


plt.plot(df['Time'], df['Risposta'], linestyle='-', color='black', label='E[Ts]')

plt.xlabel('Time')
plt.ylabel('E[Ts] nel nodo')
plt.title('E[Ts] nel centro servizi Sociali con E(S) = 12.5 minuti')
plt.legend()
plt.show()
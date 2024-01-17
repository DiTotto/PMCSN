import pandas as pd
import matplotlib.pyplot as plt


df = pd.read_csv('outputCSV/scolastico/jobServiti.csv')
df = df.sort_values(by='Time')


plt.plot(df['Time'], df['TotalJobs'], linestyle='-', color='black', label='Total Jobs')

plt.xlabel('Time')
plt.ylabel('Job nel nodo')
plt.title('Job totali nel centro scolastico con E(S) = 7.5 minuti')
plt.legend()
plt.show()


df = pd.read_csv('outputCSV/scolastico/rho.csv')
df = df.sort_values(by='Time')


plt.plot(df['Time'], df['Rho'], linestyle='-', color='black', label='Utilizzazione')

plt.xlabel('Time')
plt.ylabel('Utilizzazione nodo')
plt.title('Utilizzazione nel centro scolastico con E(S) = 7.5 minuti')
plt.legend()
plt.show()

df = pd.read_csv('outputCSV/scolastico/attesa.csv')
df = df.sort_values(by='Time')


plt.plot(df['Time'], df['Attesa'], linestyle='-', color='black', label='E[Tq]')

plt.xlabel('Time')
plt.ylabel('E[Tq] nel nodo')
plt.title('E[Tq] nel centro scolastico con E(S) = 7.5 minuti')
plt.legend()
plt.show()
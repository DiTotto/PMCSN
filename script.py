import pandas as pd
import matplotlib.pyplot as plt


# df = pd.read_csv('PMCSN/outputCSV/cultura/jobServiti.csv')
# df = df.sort_values(by='Time')


# plt.plot(df['Time'], df['TotalJobs'], linestyle='-', color='black', label='Total Jobs')

# plt.xlabel('Time')
# plt.ylabel('Job nel nodo')
# plt.title('Job totali nel centro cultura con E(S) = 240 minuti')
# plt.legend()
# plt.show()


# df = pd.read_csv('outputCSV/cultura/rho.csv')
# df = df.sort_values(by='Time')


# plt.plot(df['Time'], df['Rho'], linestyle='-', color='black', label='Utilizzazione')

# plt.yscale('log')

# plt.grid(True)

# plt.xlabel('Time')
# plt.ylabel('Utilizzazione nodo')
# plt.title('Utilizzazione nel centro Centralino con E(S) = 30 secondi')
# plt.legend()
# plt.show()

df = pd.read_csv('outputCSV/scolastico/attesa.csv')
df = df.sort_values(by='Time')


plt.plot(df['Time'], df['Risposta'], linestyle='-', color='black', label='E[Ts]')

plt.yscale('log')
plt.ylim(0, 100000000)

plt.grid(True)

plt.axhline(y=119.319, color='red', linestyle='--', label='Valor medio atteso')

#servizisociali 46.042
#servizi scolastici 119.319
#cultura 570.55

plt.xlabel('Time')
plt.ylabel('E[Ts] nel nodo')
plt.title('E[Ts] nel centro Servizi Scolastici')
plt.legend()
plt.show()
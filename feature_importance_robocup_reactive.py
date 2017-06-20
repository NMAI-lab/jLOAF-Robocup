import numpy as np
import pandas as pd

from sklearn.datasets import make_classification
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import Imputer

# Build a classification task using 3 informative features
data = pd.read_csv("Reactive_agent.txt", na_values = ['.'])
X = data.drop("Action", axis=1)
y = data["Action"]

columns = X.columns

# Build a forest and compute the feature importances
imputer = Imputer(strategy="mean",axis=0)

X = imputer.fit_transform(X)

forest = ExtraTreesClassifier(n_estimators=250, random_state=42)
forest.fit(X,y)

importances = forest.feature_importances_
indices = np.argsort(importances)[::-1]

# Print the feature ranking
print("Feature ranking:")

for f in range(X.shape[1]):
    print("%d. %s: %f" % (f + 1, columns[indices[f]], importances[indices[f]]))
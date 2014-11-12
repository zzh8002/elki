package experimentalcode.erich.intrinsicdimensionality;

/*
 This file is part of ELKI:
 Environment for Developing KDD-Applications Supported by Index-Structures

 Copyright (C) 2014
 Ludwig-Maximilians-Universität München
 Lehr- und Forschungseinheit für Datenbanksysteme
 ELKI Development Team

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import de.lmu.ifi.dbs.elki.utilities.datastructures.arraylike.NumberArrayAdapter;
import de.lmu.ifi.dbs.elki.math.statistics.ProbabilityWeightedMoments;
import de.lmu.ifi.dbs.elki.utilities.datastructures.arraylike.ArrayLikeUtil;

/**
 * Probability weighted moments based estimator using L-Moments
 * 
 * @author Jonathan von Brünken
 * @author Erich Schubert
 */
public class LMomentsPWMEstimator implements IntrinsicDimensionalityEstimator {
  @Override
  public <A> double estimate(A data, NumberArrayAdapter<?, A> adapter) {
    final int n = adapter.size(data);
    double w = adapter.getDouble(data, n - 1);
    double[] excess = new double[n];
    for(int i = 0; i < n; ++i) {
      excess[i] = w - adapter.getDouble(data, n - i - 1);
    }
    double[] lmom = ProbabilityWeightedMoments.samLMR(excess, ArrayLikeUtil.doubleArrayAdapter(), 2);
    return w / ((lmom[0] * lmom[0] / lmom[1]) - lmom[0]);
  }
}

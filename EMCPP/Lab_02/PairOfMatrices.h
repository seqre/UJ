#ifndef MEC_PAIROFMATRICES_H
#define MEC_PAIROFMATRICES_H

#include "Matrix.h"

struct PairOfMatrices {
    Matrix left;
    Matrix right;

    PairOfMatrices(const PairOfMatrices&) = delete;

    PairOfMatrices& operator=(const PairOfMatrices&) = delete;

    PairOfMatrices(PairOfMatrices&&) = default;

    PairOfMatrices& operator=(PairOfMatrices&&) = default;

};


#endif //MEC_PAIROFMATRICES_H

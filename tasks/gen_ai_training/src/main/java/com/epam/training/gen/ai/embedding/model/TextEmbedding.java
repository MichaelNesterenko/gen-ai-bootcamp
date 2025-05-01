package com.epam.training.gen.ai.embedding.model;

import java.util.List;

import com.microsoft.semantickernel.data.vectorstorage.annotations.VectorStoreRecordData;
import com.microsoft.semantickernel.data.vectorstorage.annotations.VectorStoreRecordKey;
import com.microsoft.semantickernel.data.vectorstorage.annotations.VectorStoreRecordVector;
import com.microsoft.semantickernel.data.vectorstorage.definition.DistanceFunction;
import com.microsoft.semantickernel.data.vectorstorage.definition.IndexKind;

import lombok.Data;

@Data
public class TextEmbedding {
    @VectorStoreRecordKey
    String key;

    @VectorStoreRecordData(isFullTextSearchable = true)
    String payload;

    @VectorStoreRecordVector(dimensions = 1536, distanceFunction = DistanceFunction.COSINE_DISTANCE, indexKind = IndexKind.HNSW)
    List<Float> embedding;
}

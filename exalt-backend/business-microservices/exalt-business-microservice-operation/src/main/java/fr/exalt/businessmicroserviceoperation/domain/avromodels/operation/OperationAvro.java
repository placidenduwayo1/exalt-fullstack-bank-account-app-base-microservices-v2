/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package fr.exalt.businessmicroserviceoperation.domain.avromodels.operation;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class OperationAvro extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -1983892407663299830L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OperationAvro\",\"namespace\":\"fr.exalt.businessmicroserviceoperation.domain.avromodels.operation\",\"fields\":[{\"name\":\"operationId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"mount\",\"type\":\"double\"},{\"name\":\"createdAt\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"accountId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"bankAccount\",\"type\":{\"type\":\"record\",\"name\":\"BankAccountAvro\",\"fields\":[{\"name\":\"accountId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"state\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"balance\",\"type\":\"double\"},{\"name\":\"overdraft\",\"type\":\"double\"},{\"name\":\"customerId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"customer\",\"type\":{\"type\":\"record\",\"name\":\"CustomerAvro\",\"fields\":[{\"name\":\"customerId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"firstname\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"lastname\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"state\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"email\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}}]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OperationAvro> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OperationAvro> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<OperationAvro> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<OperationAvro> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<OperationAvro> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this OperationAvro to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a OperationAvro from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a OperationAvro instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static OperationAvro fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String operationId;
  private java.lang.String type;
  private double mount;
  private java.lang.String createdAt;
  private java.lang.String accountId;
  private fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro bankAccount;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OperationAvro() {}

  /**
   * All-args constructor.
   * @param operationId The new value for operationId
   * @param type The new value for type
   * @param mount The new value for mount
   * @param createdAt The new value for createdAt
   * @param accountId The new value for accountId
   * @param bankAccount The new value for bankAccount
   */
  public OperationAvro(java.lang.String operationId, java.lang.String type, java.lang.Double mount, java.lang.String createdAt, java.lang.String accountId, fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro bankAccount) {
    this.operationId = operationId;
    this.type = type;
    this.mount = mount;
    this.createdAt = createdAt;
    this.accountId = accountId;
    this.bankAccount = bankAccount;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return operationId;
    case 1: return type;
    case 2: return mount;
    case 3: return createdAt;
    case 4: return accountId;
    case 5: return bankAccount;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: operationId = value$ != null ? value$.toString() : null; break;
    case 1: type = value$ != null ? value$.toString() : null; break;
    case 2: mount = (java.lang.Double)value$; break;
    case 3: createdAt = value$ != null ? value$.toString() : null; break;
    case 4: accountId = value$ != null ? value$.toString() : null; break;
    case 5: bankAccount = (fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'operationId' field.
   * @return The value of the 'operationId' field.
   */
  public java.lang.String getOperationId() {
    return operationId;
  }


  /**
   * Sets the value of the 'operationId' field.
   * @param value the value to set.
   */
  public void setOperationId(java.lang.String value) {
    this.operationId = value;
  }

  /**
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public java.lang.String getType() {
    return type;
  }


  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.String value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'mount' field.
   * @return The value of the 'mount' field.
   */
  public double getMount() {
    return mount;
  }


  /**
   * Sets the value of the 'mount' field.
   * @param value the value to set.
   */
  public void setMount(double value) {
    this.mount = value;
  }

  /**
   * Gets the value of the 'createdAt' field.
   * @return The value of the 'createdAt' field.
   */
  public java.lang.String getCreatedAt() {
    return createdAt;
  }


  /**
   * Sets the value of the 'createdAt' field.
   * @param value the value to set.
   */
  public void setCreatedAt(java.lang.String value) {
    this.createdAt = value;
  }

  /**
   * Gets the value of the 'accountId' field.
   * @return The value of the 'accountId' field.
   */
  public java.lang.String getAccountId() {
    return accountId;
  }


  /**
   * Sets the value of the 'accountId' field.
   * @param value the value to set.
   */
  public void setAccountId(java.lang.String value) {
    this.accountId = value;
  }

  /**
   * Gets the value of the 'bankAccount' field.
   * @return The value of the 'bankAccount' field.
   */
  public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro getBankAccount() {
    return bankAccount;
  }


  /**
   * Sets the value of the 'bankAccount' field.
   * @param value the value to set.
   */
  public void setBankAccount(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro value) {
    this.bankAccount = value;
  }

  /**
   * Creates a new OperationAvro RecordBuilder.
   * @return A new OperationAvro RecordBuilder
   */
  public static fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder newBuilder() {
    return new fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder();
  }

  /**
   * Creates a new OperationAvro RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OperationAvro RecordBuilder
   */
  public static fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder newBuilder(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder other) {
    if (other == null) {
      return new fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder();
    } else {
      return new fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder(other);
    }
  }

  /**
   * Creates a new OperationAvro RecordBuilder by copying an existing OperationAvro instance.
   * @param other The existing instance to copy.
   * @return A new OperationAvro RecordBuilder
   */
  public static fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder newBuilder(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro other) {
    if (other == null) {
      return new fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder();
    } else {
      return new fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder(other);
    }
  }

  /**
   * RecordBuilder for OperationAvro instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OperationAvro>
    implements org.apache.avro.data.RecordBuilder<OperationAvro> {

    private java.lang.String operationId;
    private java.lang.String type;
    private double mount;
    private java.lang.String createdAt;
    private java.lang.String accountId;
    private fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro bankAccount;
    private fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro.Builder bankAccountBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.operationId)) {
        this.operationId = data().deepCopy(fields()[0].schema(), other.operationId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.type)) {
        this.type = data().deepCopy(fields()[1].schema(), other.type);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.mount)) {
        this.mount = data().deepCopy(fields()[2].schema(), other.mount);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[3].schema(), other.createdAt);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.accountId)) {
        this.accountId = data().deepCopy(fields()[4].schema(), other.accountId);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.bankAccount)) {
        this.bankAccount = data().deepCopy(fields()[5].schema(), other.bankAccount);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (other.hasBankAccountBuilder()) {
        this.bankAccountBuilder = fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro.newBuilder(other.getBankAccountBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing OperationAvro instance
     * @param other The existing instance to copy.
     */
    private Builder(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.operationId)) {
        this.operationId = data().deepCopy(fields()[0].schema(), other.operationId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.type)) {
        this.type = data().deepCopy(fields()[1].schema(), other.type);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.mount)) {
        this.mount = data().deepCopy(fields()[2].schema(), other.mount);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[3].schema(), other.createdAt);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.accountId)) {
        this.accountId = data().deepCopy(fields()[4].schema(), other.accountId);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.bankAccount)) {
        this.bankAccount = data().deepCopy(fields()[5].schema(), other.bankAccount);
        fieldSetFlags()[5] = true;
      }
      this.bankAccountBuilder = null;
    }

    /**
      * Gets the value of the 'operationId' field.
      * @return The value.
      */
    public java.lang.String getOperationId() {
      return operationId;
    }


    /**
      * Sets the value of the 'operationId' field.
      * @param value The value of 'operationId'.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder setOperationId(java.lang.String value) {
      validate(fields()[0], value);
      this.operationId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'operationId' field has been set.
      * @return True if the 'operationId' field has been set, false otherwise.
      */
    public boolean hasOperationId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'operationId' field.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder clearOperationId() {
      operationId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'type' field.
      * @return The value.
      */
    public java.lang.String getType() {
      return type;
    }


    /**
      * Sets the value of the 'type' field.
      * @param value The value of 'type'.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder setType(java.lang.String value) {
      validate(fields()[1], value);
      this.type = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder clearType() {
      type = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'mount' field.
      * @return The value.
      */
    public double getMount() {
      return mount;
    }


    /**
      * Sets the value of the 'mount' field.
      * @param value The value of 'mount'.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder setMount(double value) {
      validate(fields()[2], value);
      this.mount = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'mount' field has been set.
      * @return True if the 'mount' field has been set, false otherwise.
      */
    public boolean hasMount() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'mount' field.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder clearMount() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'createdAt' field.
      * @return The value.
      */
    public java.lang.String getCreatedAt() {
      return createdAt;
    }


    /**
      * Sets the value of the 'createdAt' field.
      * @param value The value of 'createdAt'.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder setCreatedAt(java.lang.String value) {
      validate(fields()[3], value);
      this.createdAt = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'createdAt' field has been set.
      * @return True if the 'createdAt' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'createdAt' field.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder clearCreatedAt() {
      createdAt = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'accountId' field.
      * @return The value.
      */
    public java.lang.String getAccountId() {
      return accountId;
    }


    /**
      * Sets the value of the 'accountId' field.
      * @param value The value of 'accountId'.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder setAccountId(java.lang.String value) {
      validate(fields()[4], value);
      this.accountId = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'accountId' field has been set.
      * @return True if the 'accountId' field has been set, false otherwise.
      */
    public boolean hasAccountId() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'accountId' field.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder clearAccountId() {
      accountId = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'bankAccount' field.
      * @return The value.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro getBankAccount() {
      return bankAccount;
    }


    /**
      * Sets the value of the 'bankAccount' field.
      * @param value The value of 'bankAccount'.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder setBankAccount(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro value) {
      validate(fields()[5], value);
      this.bankAccountBuilder = null;
      this.bankAccount = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'bankAccount' field has been set.
      * @return True if the 'bankAccount' field has been set, false otherwise.
      */
    public boolean hasBankAccount() {
      return fieldSetFlags()[5];
    }

    /**
     * Gets the Builder instance for the 'bankAccount' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro.Builder getBankAccountBuilder() {
      if (bankAccountBuilder == null) {
        if (hasBankAccount()) {
          setBankAccountBuilder(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro.newBuilder(bankAccount));
        } else {
          setBankAccountBuilder(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro.newBuilder());
        }
      }
      return bankAccountBuilder;
    }

    /**
     * Sets the Builder instance for the 'bankAccount' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */

    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder setBankAccountBuilder(fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro.Builder value) {
      clearBankAccount();
      bankAccountBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'bankAccount' field has an active Builder instance
     * @return True if the 'bankAccount' field has an active Builder instance
     */
    public boolean hasBankAccountBuilder() {
      return bankAccountBuilder != null;
    }

    /**
      * Clears the value of the 'bankAccount' field.
      * @return This builder.
      */
    public fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro.Builder clearBankAccount() {
      bankAccount = null;
      bankAccountBuilder = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OperationAvro build() {
      try {
        OperationAvro record = new OperationAvro();
        record.operationId = fieldSetFlags()[0] ? this.operationId : (java.lang.String) defaultValue(fields()[0]);
        record.type = fieldSetFlags()[1] ? this.type : (java.lang.String) defaultValue(fields()[1]);
        record.mount = fieldSetFlags()[2] ? this.mount : (java.lang.Double) defaultValue(fields()[2]);
        record.createdAt = fieldSetFlags()[3] ? this.createdAt : (java.lang.String) defaultValue(fields()[3]);
        record.accountId = fieldSetFlags()[4] ? this.accountId : (java.lang.String) defaultValue(fields()[4]);
        if (bankAccountBuilder != null) {
          try {
            record.bankAccount = this.bankAccountBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("bankAccount"));
            throw e;
          }
        } else {
          record.bankAccount = fieldSetFlags()[5] ? this.bankAccount : (fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro) defaultValue(fields()[5]);
        }
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OperationAvro>
    WRITER$ = (org.apache.avro.io.DatumWriter<OperationAvro>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OperationAvro>
    READER$ = (org.apache.avro.io.DatumReader<OperationAvro>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.operationId);

    out.writeString(this.type);

    out.writeDouble(this.mount);

    out.writeString(this.createdAt);

    out.writeString(this.accountId);

    this.bankAccount.customEncode(out);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.operationId = in.readString();

      this.type = in.readString();

      this.mount = in.readDouble();

      this.createdAt = in.readString();

      this.accountId = in.readString();

      if (this.bankAccount == null) {
        this.bankAccount = new fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro();
      }
      this.bankAccount.customDecode(in);

    } else {
      for (int i = 0; i < 6; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.operationId = in.readString();
          break;

        case 1:
          this.type = in.readString();
          break;

        case 2:
          this.mount = in.readDouble();
          break;

        case 3:
          this.createdAt = in.readString();
          break;

        case 4:
          this.accountId = in.readString();
          break;

        case 5:
          if (this.bankAccount == null) {
            this.bankAccount = new fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.BankAccountAvro();
          }
          this.bankAccount.customDecode(in);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}











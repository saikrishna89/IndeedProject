package com.qc  ;

import com4j.*;

/**
 * For HP use. Services for managing sessions.
 */
@IID("{9B1C51AD-A124-465D-96EE-39DB5168FEFC}")
public interface IExtensionServiceProvider extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Current OTA connection.
   * </p>
   * <p>
   * Getter method for the COM property "QCConnection"
   * </p>
   * @return  Returns a value of type com.qc.ITDConnection
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  com.qc.ITDConnection qcConnection();


  /**
   * <p>
   * Current http connection.
   * </p>
   * <p>
   * Getter method for the COM property "WebConnection"
   * </p>
   * @return  Returns a value of type com4j.Com4jObject
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(8)
  com4j.Com4jObject webConnection();


  /**
   * <p>
   * Interface providing cache related services.
   * </p>
   * <p>
   * Getter method for the COM property "CacheManager"
   * </p>
   * @return  Returns a value of type com.qc.ICacheManager
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(9)
  com.qc.ICacheManager cacheManager();


  /**
   * <p>
   * Interface providing system fields related services.
   * </p>
   * <p>
   * Getter method for the COM property "SystemFieldService"
   * </p>
   * @return  Returns a value of type com.qc.ISystemFieldService
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(10)
  com.qc.ISystemFieldService systemFieldService();


  /**
   * <p>
   * Interface providing data management related services.
   * </p>
   * <p>
   * Getter method for the COM property "DataServiceProvider"
   * </p>
   * @return  Returns a value of type com.qc.IDataServiceProvider
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(11)
  com.qc.IDataServiceProvider dataServiceProvider();


  /**
   * <p>
   * Interface providing permission related services.
   * </p>
   * <p>
   * Getter method for the COM property "CustomizationPermission"
   * </p>
   * @return  Returns a value of type com.qc.ICustomizationPermissionService
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(12)
  com.qc.ICustomizationPermissionService customizationPermission();


  /**
   * <p>
   * For HP use. Interface providing factories creation and other factory services from outside OTA.
   * </p>
   * <p>
   * Getter method for the COM property "FactoryService"
   * </p>
   * @return  Returns a value of type com.qc.IFactoryService
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(13)
  com.qc.IFactoryService factoryService();


  /**
   * <p>
   * Returns the extended storage for specific extension.
   * </p>
   * <p>
   * Getter method for the COM property "ExtendedStorage"
   * </p>
   * @param extensionName Mandatory java.lang.String parameter.
   * @param serverRelativePath Mandatory java.lang.String parameter.
   * @return  Returns a value of type com.qc.IExtendedStorage
   */

  @DISPID(8) //= 0x8. The runtime will prefer the VTID if present
  @VTID(14)
  com.qc.IExtendedStorage extendedStorage(
    java.lang.String extensionName,
    java.lang.String serverRelativePath);


  // Properties:
}

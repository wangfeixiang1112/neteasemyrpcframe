package com.netease.rpc.rpc.protocol.nrpc;

import com.netease.rpc.common.tools.SpiUtils;
import com.netease.rpc.common.tools.URIUtils;
import com.netease.rpc.remoting.Transporter;
import com.netease.rpc.rpc.protocol.Protocol;

import java.net.URI;

public class NrpcProtocol implements Protocol {
    @Override
    public void export(URI exportUri) {
        // 3. 底层网络框架
        String transporterName = URIUtils.getParam(exportUri, "transporter");
        Transporter transporter = (Transporter) SpiUtils.getServiceImpl(transporterName, Transporter.class);
    }
}
